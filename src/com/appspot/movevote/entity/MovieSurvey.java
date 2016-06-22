package com.appspot.movevote.entity;

public class MovieSurvey {
	private String userId;
	private String surveyId;
	private int nextQns;

	public MovieSurvey(String userId, int nextQns) {
		this.userId = userId;
		this.nextQns = nextQns;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getSurveyId() {
		return surveyId;
	}

	public void setSurveyId(String surveyId) {
		this.surveyId = surveyId;
	}

	public int getNextQns() {
		return nextQns;
	}

	public void setNextQns(int nextQns) {
		this.nextQns = nextQns;
	}
}