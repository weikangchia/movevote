package com.appspot.movevote.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.appspot.movevote.db.InSingMovieDB;
import com.appspot.movevote.entity.Constant;
import com.appspot.movevote.entity.InSingMovie;
import com.appspot.movevote.entity.User;
import com.appspot.movevote.helper.GitkitHelper;
import com.google.identitytoolkit.GitkitUser;

/**
 * Servlet implementation class DiscoverServlet
 */
public class DiscoverServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public DiscoverServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		boolean isVerified = false;

		GitkitHelper gitkitHelper = new GitkitHelper(this);
		GitkitUser gitkitUser = gitkitHelper.validateLogin((HttpServletRequest) request);

		if (gitkitUser == null) {
			response.sendRedirect(request.getContextPath() + Constant.LOGIN_PATH);
			return;
		} else {
			isVerified = User.checkIsUserVerified(request.getCookies(),
					gitkitHelper.getGitkitClient());
			
			User userInfo = new User(gitkitUser.getLocalId(), gitkitUser.getName(),
					gitkitUser.getPhotoUrl(), gitkitUser.getEmail(),
					gitkitUser.getCurrentProvider(), isVerified);
			request.setAttribute("userInfo", userInfo);

			ArrayList<InSingMovie> movieList = InSingMovieDB.retrieveMovieList();
			request.setAttribute("nowMovieList", movieList);

			request.setAttribute("isLoggedIn", true);
			getServletContext().getRequestDispatcher("/discover.jsp").forward(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	}
}