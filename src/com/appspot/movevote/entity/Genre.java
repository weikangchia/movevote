package com.appspot.movevote.entity;

public class Genre {
	private String name;
	private int id;

	public Genre(int id, String name) {
		this.id = id;
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}