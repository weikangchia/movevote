package com.appspot.movevote.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.CompositeFilter;
import com.google.appengine.api.datastore.Query.CompositeFilterOperator;
import com.google.appengine.api.datastore.Query.Filter;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.FilterPredicate;

public class MovieEvent {
	private String eventId;
	private String userId;
	private String tmdbId;
	private String eventAction;
	private int rating;

	public MovieEvent() {

	}

	public MovieEvent(String userId, String eventAction) {
		this.userId = userId;
		this.eventAction = eventAction;
	}

	public MovieEvent(String userId, String tmdbId, String eventAction) {
		this.userId = userId;
		this.tmdbId = tmdbId;
		this.eventAction = eventAction;
	}

	public MovieEvent(String userId, String tmdbId, String eventAction, int rating) {
		this(userId, tmdbId, eventAction);
		this.rating = rating;
	}

	public void storeEventRecord() {
		DatastoreService dataStore = DatastoreServiceFactory.getDatastoreService();

		Entity eventEntity = getSpecificEventRecord();
		if (eventEntity == null) {
			eventEntity = new Entity(Constant.DS_TABLE_MOVIE_EVENT);
			eventEntity.setProperty("userId", userId);
			eventEntity.setProperty("tmdbId", tmdbId);
		}

		switch (eventAction) {
		case Constant.MOVIE_EVENT_ACTION_CLICK:
			eventEntity.setProperty("action", eventAction);
			eventEntity.setProperty("last_update", new Date());
			break;
		case Constant.MOVIE_EVENT_ACTION_RATE:
			eventEntity.setProperty("rating", rating);
			eventEntity.setProperty("action", eventAction);
			eventEntity.setProperty("last_update", new Date());
			break;
		case Constant.MOVIE_EVENT_ACTION_WANT_TO_WATCH:
			eventEntity.setProperty("action", eventAction);
			eventEntity.setProperty("last_update", new Date());
			break;
		case Constant.MOVIE_EVENT_ACTION_WATCH:
			eventEntity.setProperty("action", eventAction);
			eventEntity.setProperty("last_update", new Date());
			break;
		}

		dataStore.put(eventEntity);
	}

	public Entity getSpecificEventRecord() {
		DatastoreService dataStore = DatastoreServiceFactory.getDatastoreService();

		Filter tmdbIdFilter = new FilterPredicate("tmdbId", FilterOperator.EQUAL, tmdbId);
		Filter userIdFilter = new FilterPredicate("userId", FilterOperator.EQUAL, userId);
		Filter actionFilter = new FilterPredicate("action", FilterOperator.EQUAL, eventAction);

		CompositeFilter movieEventCompFilter = CompositeFilterOperator.and(tmdbIdFilter,
				userIdFilter, actionFilter);
		Query query = new Query(Constant.DS_TABLE_MOVIE_EVENT).setFilter(movieEventCompFilter);

		PreparedQuery pq = dataStore.prepare(query);
		Entity eventEntity = pq.asSingleEntity();

		return eventEntity;
	}

	public List<Entity> getSpecificEventRecords() {
		List<Entity> entityList = new ArrayList<Entity>();
		DatastoreService dataStore = DatastoreServiceFactory.getDatastoreService();

		Filter userIdFilter = new FilterPredicate("userId", FilterOperator.EQUAL, userId);
		Filter actionFilter = new FilterPredicate("action", FilterOperator.EQUAL, eventAction);

		CompositeFilter movieEventCompFilter = CompositeFilterOperator.and(userIdFilter,
				actionFilter);
		Query query = new Query(Constant.DS_TABLE_MOVIE_EVENT).setFilter(movieEventCompFilter);

		PreparedQuery pq = dataStore.prepare(query);
		for (Entity entity : pq.asIterable()) {
			entityList.add(entity);
		}

		return entityList;
	}

	public int getSpecificEventRecordCount() {
		DatastoreService dataStore = DatastoreServiceFactory.getDatastoreService();

		Filter userIdFilter = new FilterPredicate("userId", FilterOperator.EQUAL, userId);
		Filter actionFilter = new FilterPredicate("action", FilterOperator.EQUAL, eventAction);

		CompositeFilter movieEventCompFilter = CompositeFilterOperator.and(userIdFilter,
				actionFilter);
		Query query = new Query(Constant.DS_TABLE_MOVIE_EVENT).setFilter(movieEventCompFilter);

		PreparedQuery pq = dataStore.prepare(query);
		return pq.countEntities(FetchOptions.Builder.withDefaults());
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

	public int getRating() {
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}

	public String getEventAction() {
		return eventAction;
	}

	public void setEventAction(String eventAction) {
		this.eventAction = eventAction;
	}
}