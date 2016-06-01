package com.appspot.movevote.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.appspot.movevote.entity.Constant;
import com.appspot.movevote.entity.MovieEvent;
import com.appspot.movevote.entity.MovieEventActionEnum;
import com.appspot.movevote.entity.TMDBMovie;
import com.appspot.movevote.entity.User;
import com.appspot.movevote.helper.GitkitHelper;
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
						MovieEventActionEnum.CLICK);
				event.storeAction();
			}

			request.setAttribute("isLoggedIn", isLoggedIn);

			getServletContext().getRequestDispatcher("/movie.jsp").forward(request, response);
		}
	}
}