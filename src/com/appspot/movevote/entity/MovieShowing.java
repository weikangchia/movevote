package com.appspot.movevote.entity;

public class MovieShowing {
	private String startDate;
	private String startTime;
	private String url;
	private String format;

	public MovieShowing(String startDate,
			String startTime, String url, String format) {
		this.startDate = startDate;
		this.startTime = startTime;
		this.url = url;
		this.format = format;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}
}