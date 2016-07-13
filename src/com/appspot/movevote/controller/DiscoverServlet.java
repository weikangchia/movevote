package com.appspot.movevote.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.appspot.movevote.db.InSingMovieDB;
import com.appspot.movevote.db.RatingDB;
import com.appspot.movevote.db.SurveyDB;
import com.appspot.movevote.entity.Constant;
import com.appspot.movevote.entity.InSingMovie;
import com.appspot.movevote.entity.Rating;
import com.appspot.movevote.entity.Survey;
import com.appspot.movevote.entity.User;
import com.appspot.movevote.helper.GitkitHelper;
import com.google.identitytoolkit.GitkitUser;

/**
 * Servlet implementation class DiscoverServlet
 */
public class DiscoverServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public DiscoverServlet() {
		super();

	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		boolean isVerified = false;

		GitkitHelper gitkitHelper = new GitkitHelper(this);
		GitkitUser gitkitUser = gitkitHelper.validateLogin((HttpServletRequest) request);

		if (gitkitUser == null) {
			response.sendRedirect(request.getContextPath() + Constant.LOGIN_PATH);
			return;
		} else {
			isVerified = User.checkIsUserVerified(request.getCookies(), gitkitHelper.getGitkitClient());

			User userInfo = new User(gitkitUser.getLocalId(), gitkitUser.getName(), gitkitUser.getPhotoUrl(),
					gitkitUser.getEmail(), gitkitUser.getCurrentProvider(), isVerified);
			request.setAttribute("userInfo", userInfo);

			ArrayList<InSingMovie> movieList = InSingMovieDB.retrieveMovieList();
			request.setAttribute("nowMovieList", movieList);

			request.setAttribute("isLoggedIn", true);

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
				int highestGenreBit = 0;
				double highestRating = -1;

				for (Map.Entry<String, Rating> entry : userRatingMap.entrySet()) {
					Rating userRating = entry.getValue();
					Rating.calculateUserPreference(ratingScore, userRating.getGenreBit(), userRating.getRating());
				}

				for (int i = 0; i < ratingScore.length; i++) {
					double tempScore = (double) ratingScore[i] / userRatingMap.size();
					if (tempScore > highestRating) {
						highestGenreBit = i;
						highestRating = tempScore;
					}
					System.out.println(i + " " + tempScore);
				}

				System.out.println("highest: " + highestGenreBit);

				ArrayList<InSingMovie> recommendMovieList = new ArrayList<InSingMovie>();
				for (int m = 0; m < movieList.size(); m++) {
					Rating tempRating = userRatingMap.get(movieList.get(m).getTmdbId());

					// skip this movie if users has rated low below average: 3
					if (tempRating != null && tempRating.getRating() < 3) {
						continue;
					}

					if (movieList.get(m).getGenreBit().charAt(highestGenreBit) == '1') {
						recommendMovieList.add(movieList.get(m));
					}
				}
				request.setAttribute("recommendMovieList", recommendMovieList);
			}

			getServletContext().getRequestDispatcher("/discover.jsp").forward(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

	}
}