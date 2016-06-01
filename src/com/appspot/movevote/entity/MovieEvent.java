package com.appspot.movevote.entity;

import java.util.Date;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;

public class MovieEvent {
	private String eventId;
	private String userId;
	private String tmdbId;
	private MovieEventActionEnum eventAction;
	private int rating;

	public MovieEvent(String userId, String tmdbId, MovieEventActionEnum eventAction) {
		this.userId = userId;
		this.tmdbId = tmdbId;
		this.eventAction = eventAction;
	}

	public MovieEvent(String userId, String tmdbId, MovieEventActionEnum eventAction, int rating) {
		this(userId, tmdbId, eventAction);
		this.rating = rating;
	}

	public void storeAction() {
		DatastoreService dataStore = DatastoreServiceFactory.getDatastoreService();

		switch (eventAction) {
		case CLICK:
			Entity clickEntity = new Entity(Constant.DS_TABLE_MOVIE_EVENT);
			clickEntity.setProperty("userId", userId);
			clickEntity.setProperty("tmdbId", tmdbId);
			clickEntity.setProperty("action", eventAction.getAction());
			clickEntity.setProperty("last_update", new Date());

			dataStore.put(clickEntity);
			break;
		}
	}

	public String getEventId() {
		return eventId;
	}

	public void setEventId(String eventId) {
		this.eventId = eventId;
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

	public MovieEventActionEnum getEventAction() {
		return eventAction;
	}

	public void setEventAction(MovieEventActionEnum eventAction) {
		this.eventAction = eventAction;
	}

	public int getRating() {
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}
}