package com.appspot.movevote.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.appspot.movevote.db.RatingDB;
import com.appspot.movevote.entity.Constant;
import com.appspot.movevote.entity.InSingMovie;
import com.appspot.movevote.entity.InSingMovieShowPlace;
import com.appspot.movevote.entity.InSingMovieShowTime;
import com.appspot.movevote.entity.MovieEvent;
import com.appspot.movevote.entity.Rating;
import com.appspot.movevote.entity.TMDBMovie;
import com.appspot.movevote.entity.User;
import com.appspot.movevote.helper.GitkitHelper;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.identitytoolkit.GitkitUser;

/**
 * Servlet implementation class MovieServlet
 */
public class MovieServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public MovieServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// check if user is login
		boolean isLoggedIn = false;
		boolean isVerified = false;
		User userInfo = null;
		GitkitHelper gitkitHelper = new GitkitHelper(this);
		GitkitUser gitkitUser = gitkitHelper.validateLogin((HttpServletRequest) request);

		if (gitkitUser == null) {
			response.sendRedirect(request.getContextPath() + Constant.LOGIN_PATH);
			return;
		} else {
			isLoggedIn = true;
			isVerified = User.checkIsUserVerified(request.getCookies(),
					gitkitHelper.getGitkitClient());

			userInfo = new User(gitkitUser.getLocalId(), gitkitUser.getName(),
					gitkitUser.getPhotoUrl(), gitkitUser.getEmail(),
					gitkitUser.getCurrentProvider(), isVerified);

			request.setAttribute("userInfo", userInfo);
		}

		request.setAttribute("isLoggedIn", isLoggedIn);

		// send error if one of this parameter are null
		if (request.getParameter("provider") != null && request.getParameter("tmdb_id") != null) {
			String provider = request.getParameter("provider");
			String tmdbId = request.getParameter("tmdb_id");

			TMDBMovie movie = new TMDBMovie(tmdbId);
			movie.retrieveAll();
			request.setAttribute("movie", movie);

			// convert movie.duration to string format
			int hours = movie.getDuration() / 60;
			int minutes = movie.getDuration() % 60;
			String duration = hours + "h " + String.format("%02d", minutes) + "min";
			request.setAttribute("duration", duration);
			request.setAttribute("directorList", movie.getCrewMapList().get("Director"));

			switch (provider) {
			case Constant.PROVIDER_INSING:
				if (request.getParameter("title2") != null
						&& request.getParameter("is_id") != null) {
					String inSingId = request.getParameter("is_id");
					String title2 = request.getParameter("title2");

					request.setAttribute("title2", title2);
					request.setAttribute("inSingId", inSingId);

					// get today and tomorrow date for now showing
					SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
					Calendar cal = Calendar.getInstance();
					request.setAttribute("today", formatter.format(cal.getTime()));
					cal.add(Calendar.DATE, 1);
					request.setAttribute("tomorrow", formatter.format(cal.getTime()));
				} else {
					response.sendError(HttpServletResponse.SC_NOT_FOUND);
				}
				break;
			case Constant.PROVIDER_TMDB:
				break;
			default:
				// not one of the provider
				response.sendError(HttpServletResponse.SC_NOT_FOUND);
			}

			Rating rate = RatingDB.getRating(userInfo.getId(), tmdbId);
			if (rate == null) {
				request.setAttribute("hasRated", false);
			} else {
				request.setAttribute("hasRated", true);
				request.setAttribute("rating", rate.getRating());
			}

			getServletContext().getRequestDispatcher("/movie.jsp").forward(request, response);
		} else if (request.getParameter("action") != null) {
			String action = request.getParameter("action");
			JsonObject respObj = new JsonObject();
			switch (action) {
			case Constant.MOVIE_EVENT_ACTION_SHOWTIME:
				if (request.getParameter("title2") != null
						&& request.getParameter("title2").length() > 0
						&& request.getParameter("id") != null
						&& request.getParameter("id").length() > 0) {
					HashMap<InSingMovieShowPlace, HashMap<String, ArrayList<InSingMovieShowTime>>> showPlaceHashMap = InSingMovie
							.retrieveShowTime(request.getParameter("id"),
									request.getParameter("title2"), request.getParameter("date"));

					if (showPlaceHashMap.isEmpty()) {
						respObj.addProperty("success", false);
					} else {
						respObj.addProperty("success", true);
						JsonArray cinemaArray = new JsonArray();

						Map<InSingMovieShowPlace, HashMap<String, ArrayList<InSingMovieShowTime>>> sortedShowPlaceHashMap = new TreeMap<InSingMovieShowPlace, HashMap<String, ArrayList<InSingMovieShowTime>>>(
								showPlaceHashMap);

						for (Map.Entry<InSingMovieShowPlace, HashMap<String, ArrayList<InSingMovieShowTime>>> entry : sortedShowPlaceHashMap
								.entrySet()) {
							InSingMovieShowPlace key = entry.getKey();
							HashMap<String, ArrayList<InSingMovieShowTime>> value = entry
									.getValue();

							JsonObject movieObj = new JsonObject();
							movieObj.addProperty("name", key.getCinemaName());
							movieObj.addProperty("address", key.getAddress());

							for (Map.Entry<String, ArrayList<InSingMovieShowTime>> showTime : value
									.entrySet()) {
								String formatKey = showTime.getKey();
								movieObj.add(formatKey, new Gson().toJsonTree(showTime.getValue()));
							}

							cinemaArray.add(movieObj);
						}

						respObj.add("cinema", cinemaArray);
					}
				} else {
					respObj.addProperty("success", false);
				}
				response.getWriter().println(respObj.toString());
				break;
			default:
				// not one of the action
				response.sendError(HttpServletResponse.SC_NOT_FOUND);
			}
		} else {
			response.sendError(HttpServletResponse.SC_NOT_FOUND);
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
	}
}