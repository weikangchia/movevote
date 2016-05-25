package com.appspot.movevote.entity;

public class YouTubeVideo {
	private String name;
	private String key;

	public YouTubeVideo(String key, String name) {
		this.key = key;
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}
}