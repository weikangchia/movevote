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

import com.appspot.movevote.entity.Constant;
import com.appspot.movevote.entity.InSingMovie;
import com.appspot.movevote.entity.InSingMovieShowPlace;
import com.appspot.movevote.entity.InSingMovieShowTime;
import com.appspot.movevote.entity.MovieEvent;
import com.appspot.movevote.entity.TMDBMovie;
import com.appspot.movevote.entity.User;
import com.appspot.movevote.helper.GitkitHelper;
import com.google.appengine.api.datastore.Entity;
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
		if (request.getParameter("provider") != null && request.getParameter("is_id") != null
				&& request.getParameter("tmdb_id") != null
				&& request.getParameter("title2") != null) {
			String provider = request.getParameter("provider");
			String inSingId = request.getParameter("is_id");
			String tmdbId = request.getParameter("tmdb_id");
			String title2 = request.getParameter("title2");

			switch (provider) {
			case Constant.PROVIDER_INSING:
				// TMDBMovie movie = TMDBMovie.getTMDBMovieOver(tmdbId);
				TMDBMovie movie = new TMDBMovie(tmdbId);
				movie.retrieveAll();
				request.setAttribute("movie", movie);

				// convert movie.duration to string format
				int hours = movie.getDuration() / 60;
				int minutes = movie.getDuration() % 60;
				String duration = hours + "h " + String.format("%02d", minutes) + "min";
				request.setAttribute("duration", duration);
				request.setAttribute("directorList", movie.getCrewMapList().get("Director"));
				request.setAttribute("title2", title2);
				request.setAttribute("inSingId", inSingId);

				// get today and tomorrow date
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
				Calendar cal = Calendar.getInstance();
				request.setAttribute("today", formatter.format(cal.getTime()));
				cal.add(Calendar.DATE, 1);
				request.setAttribute("tomorrow", formatter.format(cal.getTime()));
				break;
			default:
				// not one of the provider
				response.sendError(HttpServletResponse.SC_NOT_FOUND);
			}

			// store this click event
			MovieEvent event = new MovieEvent(userInfo.getId(), tmdbId,
					Constant.MOVIE_EVENT_ACTION_CLICK);
			event.storeEventRecord();

			// check if user has rated this movie
			event.setEventAction(Constant.MOVIE_EVENT_ACTION_RATE);
			Entity eventEntity = event.getSpecificEventRecord();
			if (eventEntity == null) {
				request.setAttribute("hasRated", false);
			} else {
				request.setAttribute("hasRated", true);
				request.setAttribute("rating", eventEntity.getProperty("rating"));
			}

			// check if user has watched this movie
			event.setEventAction(Constant.MOVIE_EVENT_ACTION_WANT_TO_WATCH);
			eventEntity = event.getSpecificEventRecord();
			if (eventEntity == null) {
				event.setEventAction(Constant.MOVIE_EVENT_ACTION_WATCH);
				eventEntity = event.getSpecificEventRecord();
				if (eventEntity != null) {
					request.setAttribute("rateWatch", Constant.MOVIE_EVENT_ACTION_WATCH);
				}
			} else {
				request.setAttribute("rateWatch", Constant.MOVIE_EVENT_ACTION_WANT_TO_WATCH);
			}

			getServletContext().getRequestDispatcher("/movie.jsp").forward(request, response);
		} else if (request.getParameter("action") != null) {
			String action = request.getParameter("action");
			JsonObject respObj = new JsonObject();
			switch (action) {
			case Constant.MOVIE_EVENT_ACTION_RATE:
				int skip = 0;
				int page = 1;

				if (request.getParameter("skip") != null
						&& request.getParameter("skip").length() > 0
						&& request.getParameter("page") != null
						&& request.getParameter("page").length() > 0) {
					try {
						skip = Integer.parseInt(request.getParameter("skip"));
						page = Integer.parseInt(request.getParameter("page"));
					} catch (NumberFormatException nfe) {
					}
				} else {
					respObj.addProperty("success", false);
				}

				// pick movies from 10 year ago and above only
				int year = Calendar.getInstance().get(Calendar.YEAR) - 10;
				TMDBMovie movie = null;
				do {
					// get a list of movie
					ArrayList<TMDBMovie> movieList = TMDBMovie.retrieveNewDiscoverList(year, page, 4.5);
					for (int i = skip; i < movieList.size(); i++) {
						MovieEvent movieEvent = new MovieEvent(userInfo.getId(),
								movieList.get(i).getId(), Constant.MOVIE_EVENT_ACTION_RATE);
						if (movieEvent.getSpecificEventRecord() == null) {
							movie = movieList.get(i);

							if (i == movieList.size() - 1) { // end of page
								page++;
								skip = 0;
							} else { // still have movie in this page
								skip = i + 1;
							}
							break;
						}
					}
					if (movie == null) {
						page++;
					}
				} while (movie == null);

				respObj.addProperty("tmdbId", movie.getId());
				respObj.addProperty("success", true);
				respObj.addProperty("title", movie.getTitle());
				respObj.addProperty("overview", movie.getOverview());
				respObj.addProperty("imageUrl", movie.getImageUrl());
				respObj.addProperty("imageBackdropUrl", movie.getImageBackUrl());
				respObj.addProperty("rating", movie.getRating());
				respObj.addProperty("releaseDate", movie.getReleaseDate());
				respObj.addProperty("tmdbId", movie.getId());
				respObj.addProperty("skip", skip);
				respObj.addProperty("page", page);

				JsonElement genreList = new Gson().toJsonTree(movie.getGenreList());
				respObj.add("genreList", genreList);

				response.getWriter().println(respObj.toString());
				break;
			case Constant.MOVIE_EVENT_ACTION_SHOWTIME:
				if (request.getParameter("title2") != null
						&& request.getParameter("title2").length() > 0
						&& request.getParameter("id") != null
						&& request.getParameter("id").length() > 0) {
					HashMap<InSingMovieShowPlace, HashMap<String, ArrayList<InSingMovieShowTime>>> showPlaceHashMap = InSingMovie
							.getShowTime(request.getParameter("id"), request.getParameter("title2"),
									request.getParameter("date"));

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
				// not one of the provider
				response.sendError(HttpServletResponse.SC_NOT_FOUND);
			}
		} else {
			response.sendError(HttpServletResponse.SC_NOT_FOUND);
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		if (req.getParameter("tmdbId") == null || req.getParameter("action") == null
				|| (req.getParameter("action").equals(Constant.MOVIE_EVENT_ACTION_RATE)
						&& req.getParameter("rating") == null)) {
			resp.sendError(HttpServletResponse.SC_NOT_FOUND);
		} else {
			JsonObject respObj = new JsonObject();

			// check if user is login
			GitkitHelper gitkitHelper = new GitkitHelper(this);
			GitkitUser gitkitUser = gitkitHelper.validateLogin((HttpServletRequest) req);

			if (gitkitUser == null) {
				resp.sendRedirect(req.getContextPath() + Constant.LOGIN_PATH);
				respObj.addProperty("success", false);
			} else {
				String tmdbId = req.getParameter("tmdbId");
				String action = req.getParameter("action");
				MovieEvent event;

				try {
					switch (action) {
					case Constant.MOVIE_EVENT_ACTION_RATE:
						int rating = Integer.parseInt(req.getParameter("rating"));
						event = new MovieEvent(gitkitUser.getLocalId(), tmdbId,
								Constant.MOVIE_EVENT_ACTION_RATE, rating);
						event.storeEventRecord();
						break;
					case Constant.MOVIE_EVENT_ACTION_WANT_TO_WATCH:
						event = new MovieEvent(gitkitUser.getLocalId(), tmdbId,
								Constant.MOVIE_EVENT_ACTION_WANT_TO_WATCH);
						event.storeEventRecord();
						break;
					case Constant.MOVIE_EVENT_ACTION_WATCH:
						event = new MovieEvent(gitkitUser.getLocalId(), tmdbId,
								Constant.MOVIE_EVENT_ACTION_WATCH);
						event.storeEventRecord();
						break;
					}

					respObj.addProperty("success", true);
				} catch (NumberFormatException nfe) {
					respObj.addProperty("success", false);
					System.out.println(nfe.getMessage());
				}
			}

			resp.getWriter().println(respObj.toString());
		}
	}
}