package com.appspot.movevote.entity;

import javax.servlet.http.Cookie;

import com.appspot.movevote.db.UserDB;
import com.google.gson.JsonObject;
import com.google.identitytoolkit.GitkitClient;
import com.google.identitytoolkit.GitkitClientException;

public class User extends Person {
	private String email;
	private boolean verified;
	private String provider;

	public User() {
		super();
		// TODO Auto-generated constructor stub
	}

	public User(String id, String name, String profilePath, String email) {
		super(id, name, profilePath);
		if (profilePath == null || profilePath.equals("")) {
			setProfilePath("/assets/img/profile/no-profile.png");
		}
		this.setEmail(email);
	}

	public User(String id, String name, String profilePath, String email, String provider,
			boolean isVerified) {
		super(id, name, profilePath);
		if (profilePath == null || profilePath.equals("")) {
			setProfilePath("/assets/img/profile/no-profile.png");
		}
		this.setEmail(email);
		this.setProvider(provider);
		this.setVerified(isVerified);
	}

	public User(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public boolean isVerified() {
		return verified;
	}

	public void setVerified(boolean verified) {
		this.verified = verified;
	}

	public String getProvider() {
		return provider;
	}

	public void setProvider(String provider) {
		this.provider = provider;
	}

	public boolean isNewUser() {
		return UserDB.userExist(getId());
	}

	public static boolean checkIsUserVerified(Cookie[] cookies, GitkitClient gitkitClient) {
		for (int i = 0; i < cookies.length; i++) {
			if (cookies[i].getName().equals(Constant.GIT_COOKIE_NAME)) {
				try {
					JsonObject json = gitkitClient.validateTokenToJson(cookies[i].getValue());
					return json.get("verified").getAsBoolean();
				} catch (GitkitClientException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
			}
		}
		return false;
	}
}