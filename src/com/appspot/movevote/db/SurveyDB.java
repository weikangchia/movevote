package com.appspot.movevote.db;

import java.util.Date;

import com.appspot.movevote.entity.Constant;
import com.appspot.movevote.entity.Survey;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.FilterPredicate;

public class SurveyDB {
	public static Survey getSurvey(String userId) {
		DatastoreService dataStore = DatastoreServiceFactory.getDatastoreService();

		Query query = new Query(Constant.DS_TABLE_MOVIE_SURVEY)
				.setFilter(new FilterPredicate(Entity.KEY_RESERVED_PROPERTY, FilterOperator.EQUAL,
						KeyFactory.createKey(Constant.DS_TABLE_MOVIE_SURVEY, userId)));

		PreparedQuery pq = dataStore.prepare(query);
		Entity result = pq.asSingleEntity();

		if (result != null) {
			Survey survey = new Survey(userId,
					Integer.parseInt(result.getProperty("genres_category").toString()));
			return survey;
		}
		return null;
	}

	public static void storeSurvey(Survey survey) {
		DatastoreService dataStore = DatastoreServiceFactory.getDatastoreService();
		Entity entity = new Entity(Constant.DS_TABLE_MOVIE_SURVEY, survey.getUserId());
		entity.setProperty("genres_category", survey.getGenresCategory());
		entity.setProperty("last_updated", new Date());

		dataStore.put(entity);
	}
}