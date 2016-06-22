package com.appspot.movevote.entity;

public class Survey {
	private String userId;
	private int genresCategory;

	public Survey(String userId, int genresCategory) {
		this.userId = userId;
		this.genresCategory = genresCategory;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public int getGenresCategory() {
		return genresCategory;
	}

	public void setGenresCategory(int genresCategory) {
		this.genresCategory = genresCategory;
	}
}