package com.appspot.movevote.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;

import com.appspot.movevote.entity.Constant;
import com.appspot.movevote.entity.InSingMovie;
import com.google.identitytoolkit.GitkitClient;
import com.google.identitytoolkit.GitkitClientException;
import com.google.identitytoolkit.GitkitUser;

/**
 * Servlet implementation class HomeServlet
 */
public class HomeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

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
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		ArrayList<InSingMovie> movieList = InSingMovie.retrieveMovieList();
		request.setAttribute("movieList", movieList);

		// check if user is login
		try {
			GitkitUser gitkitUser = null;
			GitkitClient gitkitClient = GitkitClient
					.newBuilder()
					.setGoogleClientId(
							"477425429910-r99e3tmjtvst1qsfp0ttk9gcs9ffbq68.apps.googleusercontent.com")
					.setProjectId("movevote")
					.setWidgetUrl(Constant.DEV_HOSTNAME + "gitkit")
					.setServiceAccountEmail("weikangchia@gmail.com")
					.setCookieName("gtoken")
					.setKeyStream(
							this.getClass().getResourceAsStream(
									"/assets/secret/movevote-ac049cfdd3e4.p12"))
					.build();

			gitkitUser = gitkitClient.validateTokenInRequest(request);
			String userInfo = null;
			if (gitkitUser != null) {
				userInfo = "Welcome back!<br><br> Email: "
						+ gitkitUser.getEmail() + "<br> Id: "
						+ gitkitUser.getLocalId() + "<br> Provider: "
						+ gitkitUser.getCurrentProvider();
			} else {
				System.out.println("you are not login");
			}
			
			response.setStatus(HttpServletResponse.SC_OK);
		} catch (GitkitClientException | JSONException e) {
			e.printStackTrace();
			System.out.println(HttpServletResponse.SC_NOT_FOUND);
		}

		response.addHeader("Access-Control-Allow-Origin", "*");
		response.addHeader("Access-Control-Allow-Headers",
				"Content-Type, Authorization");
		response.addHeader("Access-Control-Allow-Methods",
				"PUT, GET, POST, DELETE, OPTIONS");
		response.setStatus(HttpServletResponse.SC_OK);

		getServletContext().getRequestDispatcher("/index.jsp").forward(request,
				response);
	}
}