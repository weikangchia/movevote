package com.appspot.movevote.entity;

public enum MovieEventActionEnum {
	CLICK("click"), RATE("rate"), WANT_TO_WATCH("want_to_watch"), WATCH("watch");

	private String action;

	private MovieEventActionEnum(String action) {
		this.action = action;
	}

	public String getAction() {
		return action;
	}
}