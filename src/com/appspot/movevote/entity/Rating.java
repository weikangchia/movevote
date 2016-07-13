package com.appspot.movevote.entity;

public class Rating {
	private long ratingId;
	private String userId;
	private String tmdbId;
	private int rating;
	private String genreBit;

	public Rating(long ratingId, String userId, String tmdbId, int rating, String genreBit) {
		this.ratingId = ratingId;
		this.userId = userId;
		this.tmdbId = tmdbId;
		this.rating = rating;
		this.genreBit = genreBit;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getTmdbId() {
		return tmdbId;
	}

	public void setTmdbId(String tmdbId) {
		this.tmdbId = tmdbId;
	}

	public int getRating() {
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}

	public String getGenreBit() {
		return genreBit;
	}

	public void setGenreBit(String genreBit) {
		this.genreBit = genreBit;
	}

	public long getRatingId() {
		return ratingId;
	}

	public void setRatingId(long ratingId) {
		this.ratingId = ratingId;
	}

	public static void calculateUserPreference(double[] ratingScore, String genreBit, int rating) {
		for (int i = 0; i < genreBit.length(); i++) {
			if (genreBit.charAt(i) == '1') {
				ratingScore[i] = ratingScore[i] + rating;
			} else {
				ratingScore[i] = ratingScore[i] - (0.1);
			}
		}
	}
}