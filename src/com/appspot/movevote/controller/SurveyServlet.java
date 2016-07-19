package com.appspot.movevote.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.appspot.movevote.db.RatingDB;
import com.appspot.movevote.db.SurveyDB;
import com.appspot.movevote.entity.Survey;
import com.appspot.movevote.entity.TMDBMovie;
import com.appspot.movevote.helper.GitkitHelper;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.identitytoolkit.GitkitUser;

/**
 * Servlet implementation class SurveyServlet
 */
public class SurveyServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public SurveyServlet() {
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
		GitkitHelper gitkitHelper = new GitkitHelper(this);
		GitkitUser gitkitUser = gitkitHelper.validateLogin((HttpServletRequest) request);

		JsonObject respObj = new JsonObject();

		int surveyGenresCategory = 0;
		int skip = 0;

		if (gitkitUser == null) {
			respObj.addProperty("success", false);
			respObj.addProperty("message", "Not Authorized to access this resource.");
			response.getWriter().println(respObj.toString());
			return;
		} else {
			Survey survey = SurveyDB.getSurvey(gitkitUser.getLocalId());

			// if the user does not have a survey record, create a new one
			if (survey == null) {
				SurveyDB.storeSurvey(new Survey(gitkitUser.getLocalId(), surveyGenresCategory));
			} else {
				surveyGenresCategory = survey.getGenresCategory();
			}
		}

		boolean hasMovie = false;
		if (surveyGenresCategory != 7) {
			while (true) {
				String genres = TMDBMovie.translateGenreCategory(surveyGenresCategory);
				ArrayList<TMDBMovie> movieList = TMDBMovie.retrieveDiscoverList(1, 4.5, genres);
				for (int i = skip; i < movieList.size(); i++) {
					TMDBMovie movie = movieList.get(i);

					if (RatingDB.getRating(gitkitUser.getLocalId(), movie.getId()) == null) {
						hasMovie = true;

						respObj.addProperty("tmdbId", movie.getId());
						respObj.addProperty("title", movie.getTitle());
						respObj.addProperty("overview", movie.getOverview());
						respObj.addProperty("imageUrl", movie.getImageUrl());
						respObj.addProperty("imageBackdropUrl", movie.getImageBackUrl());
						respObj.addProperty("rating", movie.getRating());
						respObj.addProperty("releaseDate", movie.getReleaseDate());
						respObj.addProperty("genreBit", movie.getGenreBit());

						JsonElement genreList = new Gson().toJsonTree(movie.getGenreList());
						respObj.add("genreList", genreList);

						respObj.addProperty("skip", i + 1);
						respObj.addProperty("surveyGenresCategory", surveyGenresCategory);

						break;
					}

					if (i >= 2) {
						break;
					}
				}

				if (!hasMovie) {
					surveyGenresCategory += 1;
					SurveyDB.storeSurvey(new Survey(gitkitUser.getLocalId(), surveyGenresCategory));
				} else {
					break;
				}

				if (surveyGenresCategory == 7) {
					break;
				}
			}

			if (hasMovie) {
				respObj.addProperty("hasSurvey", true);
			} else {
				respObj.addProperty("hasSurvey", false);
			}
			respObj.addProperty("success", true);
		}

		response.getWriter().println(respObj.toString());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	}
}