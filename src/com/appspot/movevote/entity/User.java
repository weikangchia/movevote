package com.appspot.movevote.entity;

public class User extends Person {
	private String email;

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
}