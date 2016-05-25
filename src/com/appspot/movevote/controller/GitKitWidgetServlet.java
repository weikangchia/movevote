package com.appspot.movevote.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.appspot.movevote.helper.GitkitHelper;
import com.google.identitytoolkit.GitkitClient.OobResponse;
import com.google.identitytoolkit.GitkitServerException;

/**
 * Servlet implementation class GitKitWidgetServlet
 */
public class GitKitWidgetServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public GitKitWidgetServlet() {
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
		getServletContext().getRequestDispatcher("/gitkit-widget.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		GitkitHelper gitHelper = new GitkitHelper(this);

		// unfinished implementation
		try {
			OobResponse oobResponse = gitHelper.getGitkitClient().getOobResponse(request);
		} catch (GitkitServerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		response.setContentType("application/json");
		response.getWriter().println("{\"success\":true}");
	}
}