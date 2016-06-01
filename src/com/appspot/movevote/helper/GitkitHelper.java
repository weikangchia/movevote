package com.appspot.movevote.helper;

import java.io.FileInputStream;
import java.io.IOException;

import javax.servlet.GenericServlet;
import javax.servlet.http.HttpServletRequest;

import com.appspot.movevote.entity.Constant;
import com.google.identitytoolkit.GitkitClient;
import com.google.identitytoolkit.GitkitClientException;
import com.google.identitytoolkit.GitkitUser;

public class GitkitHelper {
	private GitkitClient gitkitClient;

	public GitkitHelper(GenericServlet gServlet) throws IOException {
		this.gitkitClient = GitkitClient.newBuilder().setGoogleClientId(Constant.GIT_CLIENT_ID)
				.setProjectId(Constant.GIT_PROJECT_ID).setWidgetUrl(Constant.GIT_PUB_WIDGET_URL)
				.setServiceAccountEmail(Constant.GIT_SERVICE_ACCOUNT_EMAIL)
				.setCookieName(Constant.GIT_COOKIE_NAME)
				.setKeyStream(new FileInputStream(gServlet.getServletContext()
						.getRealPath("/assets/secret/movevote-ac049cfdd3e4.p12")))
				.build();
	}

	public GitkitUser validateLogin(HttpServletRequest request) {
		GitkitUser gitkitUser = null;
		try {
			gitkitUser = gitkitClient.validateTokenInRequest(request);
		} catch (GitkitClientException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return gitkitUser;
	}

	public GitkitClient getGitkitClient() {
		return gitkitClient;
	}
}