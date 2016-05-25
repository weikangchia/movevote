package com.appspot.movevote.entity;

import com.appspot.movevote.helper.TMDBHelper;

public class Cast extends Person {
	private String character;

	public Cast(String id, String name, String profilePath, String character) {
		super(id, name, profilePath);
		this.character = character;
	}

	public String getCharacter() {
		return character;
	}

	public void setCharacter(String character) {
		this.character = character;
	}

	@Override
	public String getProfilePath() {
		// TODO Auto-generated method stub
		return TMDBHelper.getAbsImageUrl(super.getProfilePath(),
				Constant.TMDB_IMAGE_PROFILE_SIZE);
	}
}