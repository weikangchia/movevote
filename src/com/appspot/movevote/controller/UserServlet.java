package com.appspot.movevote.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.appspot.movevote.db.FriendDB;
import com.appspot.movevote.db.UserDB;
import com.appspot.movevote.entity.Constant;
import com.appspot.movevote.entity.User;
import com.appspot.movevote.helper.GitkitHelper;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.identitytoolkit.GitkitUser;

/**
 * Servlet implementation class UserController
 */
public class UserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public UserServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		GitkitHelper gitkitHelper = new GitkitHelper(this);
		GitkitUser gitkitUser = gitkitHelper.validateLogin((HttpServletRequest) request);

		if (gitkitUser == null) {
			response.sendRedirect(request.getContextPath() + Constant.LOGIN_PATH);
			return;
		} else {
			JsonObject respObj = new JsonObject();

			ArrayList<User> userList = UserDB.getAllUser();
			ArrayList<User> friendList = FriendDB.getFriendList(gitkitUser.getLocalId());

			userList.remove(new User(gitkitUser.getLocalId(), gitkitUser.getName()));
			userList.removeAll(friendList);

			JsonElement userElement = new Gson().toJsonTree(userList);
			respObj.add("users", userElement);
			response.getWriter().println(respObj.toString());
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String friendId, friendName;
		String action = request.getParameter("action");
		GitkitHelper gitkitHelper = new GitkitHelper(this);
		GitkitUser gitkitUser = gitkitHelper.validateLogin((HttpServletRequest) request);

		if (gitkitUser == null) {
			response.sendRedirect(request.getContextPath() + Constant.LOGIN_PATH);
			return;
		} else {
			JsonObject respObj = new JsonObject();

			if (action != null) {
				switch (action) {
				case "add":
					friendId = request.getParameter("friendId");
					friendName = request.getParameter("friendName");
					FriendDB.addFriend(gitkitUser.getLocalId(), gitkitUser.getName(), friendId, friendName);
					respObj.addProperty("success", true);
					break;
				case "remove":
					friendId = request.getParameter("friendId");
					FriendDB.removeFriend(gitkitUser.getLocalId(), friendId);
					respObj.addProperty("success", true);
					break;
				}
			}

			response.getWriter().println(respObj.toString());
		}
	}
}