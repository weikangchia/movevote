package com.appspot.movevote.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.appspot.movevote.entity.Constant;
import com.appspot.movevote.entity.MovieEvent;
import com.appspot.movevote.entity.TMDBMovie;
import com.appspot.movevote.entity.User;
import com.appspot.movevote.helper.GitkitHelper;
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
		// TODO Auto-generated method stub

		// send error if one of this parameter are null
		if (request.getParameter("provider") == null || request.getParameter("is_id") == null
				|| request.getParameter("tmdb_id") == null) {
			response.sendError(HttpServletResponse.SC_NOT_FOUND);
		} else {
			String provider = request.getParameter("provider");
			String inSingId = request.getParameter("is_id");
			String tmdbId = request.getParameter("tmdb_id");

			switch (provider) {
			case Constant.PROVIDER_INSING:
				TMDBMovie movie = TMDBMovie.retrieveTMDBIdById(tmdbId);
				request.setAttribute("movie", movie);

				// convert movie.duration to string format
				int hours = movie.getDuration() / 60;
				int minutes = movie.getDuration() % 60;
				String duration = hours + "h " + String.format("%02d", minutes) + "min";
				request.setAttribute("duration", duration);
				request.setAttribute("directorList", movie.getCrewMapList().get("Director"));
				break;
			default:
				// not one of the provider
				response.sendError(HttpServletResponse.SC_NOT_FOUND);
			}

			// check if user is login
			boolean isLoggedIn = false;
			boolean isVerified = false;
			GitkitHelper gitkitHelper = new GitkitHelper(this);
			GitkitUser gitkitUser = gitkitHelper.validateLogin((HttpServletRequest) request);

			if (gitkitUser == null) {
				response.sendRedirect(request.getContextPath() + Constant.LOGIN_PATH);
			} else {
				isLoggedIn = true;
				isVerified = User.checkIsUserVerified(request.getCookies(),
						gitkitHelper.getGitkitClient());

				User userInfo = new User(gitkitUser.getLocalId(), gitkitUser.getName(),
						gitkitUser.getPhotoUrl(), gitkitUser.getEmail(),
						gitkitUser.getCurrentProvider(), isVerified);

				request.setAttribute("userInfo", userInfo);

				// store this click event
				MovieEvent event = new MovieEvent(userInfo.getId(), tmdbId,
						Constant.MOVIE_EVENT_ACTION_CLICK);
				event.storeEventRecord();
			}

			request.setAttribute("isLoggedIn", isLoggedIn);

			getServletContext().getRequestDispatcher("/movie.jsp").forward(request, response);
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

				try {
					switch (action) {
					case Constant.MOVIE_EVENT_ACTION_RATE:

						int rating = Integer.parseInt(req.getParameter("rating"));
						MovieEvent event = new MovieEvent(gitkitUser.getLocalId(), tmdbId,
								Constant.MOVIE_EVENT_ACTION_RATE, rating);
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