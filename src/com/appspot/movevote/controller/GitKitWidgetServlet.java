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
import com.appspot.movevote.entity.User;
import com.appspot.movevote.helper.GitkitHelper;
import com.google.gson.JsonObject;
import com.google.identitytoolkit.GitkitClient.OobAction;
import com.google.identitytoolkit.GitkitClient.OobResponse;
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
 * Servlet implementation class GitKitWidgetServlet
 */
public class GitKitWidgetServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger log = Logger.getLogger(GitKitWidgetServlet.class.getName());

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

			// check if user has verified his/her account
			if (request.getParameter("mode") != null
					&& request.getParameter("mode").equals("manageAccount")) {
				request.setAttribute("mode", "manageAccount");
				if (userInfo.isVerified()) {
					request.setAttribute("isVerified", true);
				} else {
					request.setAttribute("isVerified", false);
				}
			}
		}

		request.setAttribute("isLoggedIn", isLoggedIn);
		getServletContext().getRequestDispatcher("/gitkit-widget.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		boolean success = false;
		GitkitHelper gitkitHelper = new GitkitHelper(this);
		GitkitUser gitkitUser = gitkitHelper.validateLogin(request);
		JsonObject respObj = new JsonObject();

		// initialize mailjet client
		MailjetClient client = new MailjetClient(Constant.MAILJET_API_Key,
				Constant.MAILJET_Secret_Key);
		MailjetRequest email = null;
		JSONArray recipients;
		MailjetResponse mailResponse = null;

		if (request.getParameter("action") != null && gitkitUser != null) {
			if (request.getParameter("action").equals("resendVerficationLink")) {
				// generate verification link
				String verificationLink = null;
				try {
					verificationLink = gitkitHelper.getGitkitClient()
							.getEmailVerificationLink(gitkitUser.getEmail());
				} catch (GitkitServerException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					log.warning("unable to create verification link for " + gitkitUser.getEmail());
				} catch (GitkitClientException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					log.warning("unable to create verification link for " + gitkitUser.getEmail());
				}
				recipients = new JSONArray()
						.put(new JSONObject().put(Contact.EMAIL, gitkitUser.getEmail()));

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
			}
		} else {
			try {
				OobResponse oobResponse = gitkitHelper.getGitkitClient().getOobResponse(request);
				if (oobResponse.getOobAction() != null) {
					log.info("received gitkit action: " + oobResponse.getOobAction());

					recipients = new JSONArray()
							.put(new JSONObject().put(Contact.EMAIL, oobResponse.getRecipient()));

					if (oobResponse.getOobAction().equals(OobAction.CHANGE_EMAIL)) {
						email = new MailjetRequest(Email.resource)
								.property(Email.FROMNAME, "MoveVote")
								.property(Email.FROMEMAIL, "weikangchia@gmail.com")
								.property(Email.SUBJECT,
										"Verify your new sign-in email for movevote")
								.property(Email.HTMLPART,
										"<p>Hi,</p><p>You have just requested to change your sign-in email to "
												+ oobResponse.getRecipient()
												+ "</p><p>Follow this link to finish changing your email address.</p><a href=\""
												+ oobResponse.getOobUrl().get() + "\">"
												+ oobResponse.getOobUrl().get()
												+ "</a><p>If you didn’t ask to change your email address, you can ignore this email.</p><p>Thanks,<br /> Your MoveVote team</p>")
								.property(Email.RECIPIENTS, recipients)
								.property(Email.MJCUSTOMID, "JAVA-Email");
					} else if (oobResponse.getOobAction().equals(OobAction.RESET_PASSWORD)) {
						email = new MailjetRequest(Email.resource)
								.property(Email.FROMNAME, "MoveVote")
								.property(Email.FROMEMAIL, "weikangchia@gmail.com")
								.property(Email.SUBJECT, "Reset your password for movevote")
								.property(Email.HTMLPART,
										"<p>Hi,</p><p>Follow this link to reset your movevote password for your "
												+ oobResponse.getRecipient() + " account.</p>"
												+ "<a href=\"" + oobResponse.getOobUrl().get()
												+ "\">" + oobResponse.getOobUrl().get()
												+ "</a><p>If you didn’t ask to reset your password, you can ignore this email.</p><p>Thanks,<br /> Your MoveVote team</p>")
								.property(Email.RECIPIENTS, recipients)
								.property(Email.MJCUSTOMID, "JAVA-Email");
					}
				}
			} catch (GitkitServerException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		if (email != null) {
			try {
				mailResponse = client.post(email);
			} catch (MailjetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				if (mailResponse.getStatus() == 200) {
					success = true;
				}
			}
		}

		response.setContentType("application/json");

		if (success) {
			respObj.addProperty("success", true);
		} else {
			respObj.addProperty("success", false);
		}

		response.getWriter().println(respObj.toString());
	}
}