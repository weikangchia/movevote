package com.appspot.movevote.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.appspot.movevote.entity.Constant;
import com.appspot.movevote.entity.TMDBMovie;

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
			response.sendRedirect("/home");
		}

		getServletContext().getRequestDispatcher("/movie.jsp").forward(request, response);
	}
}