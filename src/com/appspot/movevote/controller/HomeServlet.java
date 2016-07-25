package com.appspot.movevote.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.appspot.movevote.db.InSingMovieDB;
import com.appspot.movevote.db.RatingDB;
import com.appspot.movevote.db.SurveyDB;
import com.appspot.movevote.entity.InSingMovie;
import com.appspot.movevote.entity.Rating;
import com.appspot.movevote.entity.Survey;
import com.appspot.movevote.entity.User;
import com.appspot.movevote.helper.GitkitHelper;
import com.google.identitytoolkit.GitkitUser;

/**
 * Servlet implementation class HomeServlet
 */
public class HomeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger log = Logger.getLogger(HomeServlet.class.getName());

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public HomeServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		ArrayList<InSingMovie> movieList = InSingMovieDB.retrieveMovieList();
		request.setAttribute("movieList", movieList);

		ArrayList<InSingMovie> top5RatedMovieList = InSingMovieDB.top5RatedMovieList();
		request.setAttribute("top5MovieList", top5RatedMovieList);

		// check if user is login
		boolean isLoggedIn = false;
		boolean isVerified = false;
		GitkitHelper gitkitHelper = new GitkitHelper(this);
		GitkitUser gitkitUser = gitkitHelper.validateLogin(request);

		if (gitkitUser != null) {
			isLoggedIn = true;
			isVerified = User.checkIsUserVerified(request.getCookies(), gitkitHelper.getGitkitClient());

			User userInfo = new User(gitkitUser.getLocalId(), gitkitUser.getName(), gitkitUser.getPhotoUrl(),
					gitkitUser.getEmail(), gitkitUser.getCurrentProvider(), isVerified);
			request.setAttribute("userInfo", userInfo);
			request.setAttribute("isLoggedIn", isLoggedIn);

			request.setAttribute("nowMovieList", movieList);

			// check if user has finish the movie survey
			boolean completedSurvey = false;
			Survey survey = SurveyDB.getSurvey(gitkitUser.getLocalId());
			int surveyGenresCategory = 0;
			if (survey == null) {
				SurveyDB.storeSurvey(new Survey(gitkitUser.getLocalId(), surveyGenresCategory));
			} else {
				surveyGenresCategory = survey.getGenresCategory();
			}

			if (surveyGenresCategory == 7) {
				completedSurvey = true;
			}

			// get user movie preferences only if user has completed the survey
			if (completedSurvey) {
				HashMap<String, Rating> userRatingMap = RatingDB.getRating(userInfo.getId());

				double[] ratingScore = { 0, 0, 0, 0, 0, 0, 0 };
				int[] genreCount = { 0, 0, 0, 0, 0, 0, 0 };

				for (Map.Entry<String, Rating> entry : userRatingMap.entrySet()) {
					Rating userRating = entry.getValue();

					// if user rating is -1 means the user has skip the movie.
					if (userRating.getRating() != -1) {
						Rating.calculateUserPreference(ratingScore, genreCount, userRating.getGenreBit(),
								userRating.getRating());
					}
				}

				for (int i = 0; i < ratingScore.length; i++) {
					ratingScore[i] = ratingScore[i] / genreCount[i];
				}

				ArrayList<InSingMovie> recommendMovieList = new ArrayList<InSingMovie>();
				for (int m = 0; m < movieList.size(); m++) {
					Rating tempRating = userRatingMap.get(movieList.get(m).getTmdbId());

					// skip this movie if users has rated low below average: 3
					if (tempRating != null && tempRating.getRating() < 3) {
						continue;
					} else if (tempRating != null && tempRating.getRating() >= 3) {
						recommendMovieList.add(movieList.get(m));
					} else {
						double score = 0;
						for (int i = 0; i < ratingScore.length; i++) {
							if (movieList.get(m).getGenreBit().charAt(i) == '1') {
								score += ratingScore[i];
							}
						}

						if (score >= 3) {
							recommendMovieList.add(movieList.get(m));
						}
					}
				}
				request.setAttribute("recommendMovieList", recommendMovieList);
			}

			getServletContext().getRequestDispatcher("/discover.jsp").forward(request, response);
		} else {
			request.setAttribute("isLoggedIn", isLoggedIn);
			getServletContext().getRequestDispatcher("/index.jsp").forward(request, response);
		}
	}
}