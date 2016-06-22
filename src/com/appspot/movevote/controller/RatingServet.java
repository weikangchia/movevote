package com.appspot.movevote.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.appspot.movevote.db.RatingDB;
import com.appspot.movevote.entity.Rating;
import com.appspot.movevote.helper.GitkitHelper;
import com.google.gson.JsonObject;
import com.google.identitytoolkit.GitkitUser;

/**
 * Servlet implementation class RatingServet
 */
public class RatingServet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public RatingServet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// check if user is login
		GitkitHelper gitkitHelper = new GitkitHelper(this);
		GitkitUser gitkitUser = gitkitHelper.validateLogin((HttpServletRequest) request);

		if (request.getParameter("tmdbId") == null && request.getParameter("rating") == null
				&& request.getParameter("genreBit") == null) {
			response.sendError(HttpServletResponse.SC_NOT_FOUND);
			return;
		} else if (gitkitUser == null) {
			response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
			return;
		} else {
			String tmdbId = request.getParameter("tmdbId");
			String genreBit = request.getParameter("genreBit");
			int rating = Integer.parseInt(request.getParameter("rating"));

			Rating rate = RatingDB.getRating(gitkitUser.getLocalId(), tmdbId);
			if (rate == null) {
				rate = new Rating(0, gitkitUser.getLocalId(), tmdbId, rating, genreBit);
			} else {
				rate.setRating(rating);
			}

			RatingDB.storeRating(rate);

			JsonObject respObj = new JsonObject();
			respObj.addProperty("success", true);
			response.getWriter().println(respObj.toString());
		}
	}
}