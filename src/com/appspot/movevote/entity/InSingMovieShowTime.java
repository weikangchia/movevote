package com.appspot.movevote.entity;

public class InSingMovieShowTime {
	private String format;
	private String url;
	private String timing;

	public InSingMovieShowTime(String format, String url, String timing) {
		this.format = format;
		this.url = url;
		this.timing = timing;
	}

	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getTiming() {
		return timing;
	}

	public void setTiming(String timing) {
		this.timing = timing;
	}
}