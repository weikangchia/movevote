package com.appspot.movevote.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.appspot.movevote.entity.InSingMovie;
import com.appspot.movevote.entity.User;
import com.appspot.movevote.helper.GitkitHelper;
import com.google.identitytoolkit.GitkitUser;

/**
 * Servlet implementation class HomeServlet
 */
public class HomeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger log = Logger.getLogger(HomeServlet.class.getName());

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
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		ArrayList<InSingMovie> movieList = InSingMovie.retrieveMovieList();
		request.setAttribute("movieList", movieList);

		// check if user is login
		boolean isLoggedIn = false;
		boolean isVerified = false;
		GitkitHelper gitkitHelper = new GitkitHelper(this);

		GitkitUser gitkitUser = gitkitHelper.validateLogin(request);

		if (gitkitUser != null) {
			isLoggedIn = true;
			isVerified = User.checkIsUserVerified(request.getCookies(),
					gitkitHelper.getGitkitClient());
			
			User userInfo = new User(gitkitUser.getLocalId(), gitkitUser.getName(),
					gitkitUser.getPhotoUrl(), gitkitUser.getEmail(),
					gitkitUser.getCurrentProvider(), isVerified);
			request.setAttribute("userInfo", userInfo);
		}

		request.setAttribute("isLoggedIn", isLoggedIn);

		getServletContext().getRequestDispatcher("/index.jsp").forward(request, response);
	}
}