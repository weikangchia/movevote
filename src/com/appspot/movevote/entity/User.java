package com.appspot.movevote.entity;

import java.util.Date;

import javax.servlet.http.Cookie;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.FilterPredicate;
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
		DatastoreService dataStore = DatastoreServiceFactory.getDatastoreService();
		Query query = new Query(Constant.DS_TABLE_USER)
				.setFilter(new FilterPredicate(Entity.KEY_RESERVED_PROPERTY, FilterOperator.EQUAL,
						KeyFactory.createKey(Constant.DS_TABLE_USER, getId())));

		PreparedQuery pq = dataStore.prepare(query);
		Entity result = pq.asSingleEntity();

		if (result != null) {
			return false;
		}
		return true;
	}

	public void createNewUser() {
		DatastoreService dataStore = DatastoreServiceFactory.getDatastoreService();
		Entity newUser = new Entity(Constant.DS_TABLE_USER, getId());
		newUser.setProperty("name", getName());
		newUser.setProperty("last_update", new Date());

		dataStore.put(newUser);
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