package com.appspot.movevote.db;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Logger;

import com.appspot.movevote.entity.Constant;
import com.appspot.movevote.entity.Rating;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.PreparedQuery.TooManyResultsException;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.CompositeFilter;
import com.google.appengine.api.datastore.Query.CompositeFilterOperator;
import com.google.appengine.api.datastore.Query.Filter;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.FilterPredicate;

public class RatingDB {
	private static final Logger log = Logger.getLogger(RatingDB.class.getName());

	public static Rating getRating(String userId, String tmdbId) {
		DatastoreService dataStore = DatastoreServiceFactory.getDatastoreService();

		Filter userIdFilter = new FilterPredicate("user_id", FilterOperator.EQUAL, userId);
		Filter tmdbIdFilter = new FilterPredicate("tmdb_id", FilterOperator.EQUAL, tmdbId);

		CompositeFilter compFilter = CompositeFilterOperator.and(userIdFilter, tmdbIdFilter);

		Query query = new Query(Constant.DS_TABLE_RATING).setFilter(compFilter);

		PreparedQuery pq = dataStore.prepare(query);

		try {
			Entity result = pq.asSingleEntity();

			if (result != null) {
				Rating rating = new Rating(result.getKey().getId(), result.getProperty("user_id").toString(),
						result.getProperty("tmdb_id").toString(),
						Integer.parseInt(result.getProperty("rating").toString()),
						result.getProperty("genre_bit").toString());
				return rating;
			}
		} catch (TooManyResultsException manyEx) {
			List<Entity> entityList = pq.asList(FetchOptions.Builder.withDefaults());

			int i = 0;
			while (entityList.size() > 1) {
				dataStore.delete(KeyFactory.createKey(Constant.DS_TABLE_RATING, entityList.get(i).getKey().getId()));
				entityList.remove(i);
				i++;
			}
		}

		return null;
	}

	public static HashMap<String, Rating> getRating(String userId) {
		DatastoreService dataStore = DatastoreServiceFactory.getDatastoreService();

		HashMap<String, Rating> userRatingMap = new HashMap<String, Rating>();

		Filter userIdFilter = new FilterPredicate("user_id", FilterOperator.EQUAL, userId);

		Query query = new Query(Constant.DS_TABLE_RATING).setFilter(userIdFilter);

		PreparedQuery pq = dataStore.prepare(query);

		try {
			List<Entity> resultList = pq.asList(FetchOptions.Builder.withDefaults());
			for (Entity entity : resultList) {
				Rating rating = new Rating(entity.getKey().getId(), entity.getProperty("user_id").toString(),
						entity.getProperty("tmdb_id").toString(),
						Integer.parseInt(entity.getProperty("rating").toString()),
						entity.getProperty("genre_bit").toString());

				userRatingMap.put(entity.getProperty("tmdb_id").toString(), rating);
			}
		} catch (Exception ex) {
			log.warning(ex.getMessage());
		}

		return userRatingMap;
	}

	public static void storeRating(Rating rating) {
		DatastoreService dataStore = DatastoreServiceFactory.getDatastoreService();

		if (rating.getRatingId() == 0) {
			Entity rateEntity = new Entity(Constant.DS_TABLE_RATING);
			rateEntity.setProperty("user_id", rating.getUserId());
			rateEntity.setProperty("tmdb_id", rating.getTmdbId());
			rateEntity.setProperty("rating", rating.getRating());
			rateEntity.setProperty("genre_bit", rating.getGenreBit());
			rateEntity.setProperty("last_update", new Date());
			dataStore.put(rateEntity);
		} else {
			Entity rateEntity = new Entity(Constant.DS_TABLE_RATING, rating.getRatingId());
			rateEntity.setProperty("user_id", rating.getUserId());
			rateEntity.setProperty("tmdb_id", rating.getTmdbId());
			rateEntity.setProperty("rating", rating.getRating());
			rateEntity.setProperty("genre_bit", rating.getGenreBit());
			rateEntity.setProperty("last_update", new Date());
			dataStore.put(rateEntity);
		}
	}
}