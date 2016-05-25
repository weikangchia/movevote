package com.appspot.movevote.entity;

public class Person {
	private String id;
	private String name;
	private String profilePath;

	public Person() {

	}
	
	public Person(String name) {
		this.name = name;
	}

	public Person(String id, String name, String profilePath) {
		this.id = id;
		this.name = name;
		this.profilePath = profilePath;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getProfilePath() {
		return profilePath;
	}

	public void setProfilePath(String profilePath) {
		this.profilePath = profilePath;
	}
}