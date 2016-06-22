package com.appspot.movevote.cron;

import java.io.IOException;
import java.util.HashMap;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.appspot.movevote.entity.InSingMovie;

/**
 * Servlet implementation class InSingCronController
 */
public class UpdateInSingDBCronServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger log = Logger
			.getLogger(UpdateInSingDBCronServlet.class.getName());

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public UpdateInSingDBCronServlet() {
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
		log.info("Starting updating InSing now showing database...");

		HashMap<String, InSingMovie> newMovieMap = new HashMap<String, InSingMovie>();
		newMovieMap = InSingMovie.fetchFromInSing();
		InSingMovie.storeMovie(newMovieMap);

		log.info("Completed.");

		response.setContentType("text/plain");
		response.getWriter().println("Completed.");
	}
}