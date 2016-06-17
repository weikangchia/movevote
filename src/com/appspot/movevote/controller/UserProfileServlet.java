package com.appspot.movevote.controller;

import java.io.IOException;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;

import com.appspot.movevote.entity.Constant;
import com.appspot.movevote.entity.MovieEvent;
import com.appspot.movevote.entity.User;
import com.appspot.movevote.helper.GitkitHelper;
import com.google.identitytoolkit.GitkitClientException;
import com.google.identitytoolkit.GitkitServerException;
import com.google.identitytoolkit.GitkitUser;
import com.mailjet.client.MailjetClient;
import com.mailjet.client.MailjetRequest;
import com.mailjet.client.MailjetResponse;
import com.mailjet.client.errors.MailjetException;
import com.mailjet.client.resource.Contact;
import com.mailjet.client.resource.Email;

/**
 * Servlet implementation class UserServlet
 */
public class UserProfileServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger log = Logger.getLogger(UserProfileServlet.class.getName());

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public UserProfileServlet() {
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

			// check if he is a new user
			boolean isNewUser = userInfo.isNewUser();

			if (isNewUser) {
				userInfo.createNewUser();
				if (!userInfo.isVerified()) {
					// generate the verification link
					String verificationLink = null;
					try {
						verificationLink = gitkitHelper.getGitkitClient()
								.getEmailVerificationLink(userInfo.getEmail());
					} catch (GitkitServerException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						log.warning(
								"unable to create verification link for " + userInfo.getEmail());
					} catch (GitkitClientException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						log.warning(
								"unable to create verification link for " + userInfo.getEmail());
					}

					// initialize mailjet client
					MailjetClient client = new MailjetClient(Constant.MAILJET_API_Key,
							Constant.MAILJET_Secret_Key);
					MailjetRequest email = null;
					JSONArray recipients;
					MailjetResponse mailResponse = null;

					recipients = new JSONArray()
							.put(new JSONObject().put(Contact.EMAIL, userInfo.getEmail()));

					email = new MailjetRequest(Email.resource).property(Email.FROMNAME, "MoveVote")
							.property(Email.FROMEMAIL, "weikangchia@gmail.com")
							.property(Email.SUBJECT, "Verify your email for movevote")
							.property(Email.HTMLPART,
									"<p>Hello " + gitkitUser.getName()
											+ ",</p><p>Follow this link to verify your email address.</p><a href=\""
											+ verificationLink + "\">" + verificationLink
											+ "</a><p>If you didn’t ask to change your email address, you can ignore this email.</p><p>Thanks,<br /> Your MoveVote team</p>")
							.property(Email.RECIPIENTS, recipients)
							.property(Email.MJCUSTOMID, "JAVA-Email");

					try {
						mailResponse = client.post(email);
						log.info("email verification link sent to " + userInfo.getEmail());
					} catch (MailjetException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						log.info("unable to send verification link to " + userInfo.getEmail());
					}
				}
			}

			// get numbers of movies that the user has rated
			MovieEvent event = new MovieEvent(userInfo.getId(), Constant.MOVIE_EVENT_ACTION_RATE);
			int rateCount = event.getSpecificEventRecordCount();

			// get number of movies that the user has watched
			event.setEventAction(Constant.MOVIE_EVENT_ACTION_WATCH);
			int watchedCount = event.getSpecificEventRecordCount();

			// get number of movies that the user want to watch
			event.setEventAction(Constant.MOVIE_EVENT_ACTION_WANT_TO_WATCH);
			int wantToWatchCount = event.getSpecificEventRecordCount();

			request.setAttribute("rateCount", rateCount);
			request.setAttribute("watchedCount", watchedCount);
			request.setAttribute("wantToWatchCount", wantToWatchCount);
		}

		request.setAttribute("isLoggedIn", isLoggedIn);
		getServletContext().getRequestDispatcher("/user_profile.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub

	}
}