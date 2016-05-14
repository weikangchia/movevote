package com.appspot.movevote.entity;

public class Movie {
	private String title;
	private String id;
	private String imageUrl;

	public Movie(String id) {
		this.id = id;
	}

	public Movie(String id, String title, String imageUrl) {
		this.title = title;
		this.id = id;
		this.imageUrl = imageUrl;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}