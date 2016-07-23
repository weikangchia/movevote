package com.appspot.movevote.db;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.appspot.movevote.entity.Constant;
import com.appspot.movevote.entity.InSingMovie;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.SortDirection;

public class InSingMovieDB {
	public static void storeInSingMovie(InSingMovie movie) {
		DatastoreService dataStore = DatastoreServiceFactory.getDatastoreService();

		Entity movieEntity = new Entity(Constant.DS_TABLE_INSING_MOVIE_NOW_SHOWING, movie.getId());
		movieEntity.setProperty("title", movie.getTitle());
		movieEntity.setProperty("title2", movie.getTitle2());
		movieEntity.setProperty("image_url", movie.getImageUrl());
		movieEntity.setProperty("tmdb_id", movie.getTmdbId());
		movieEntity.setProperty("last_update", new Date());
		movieEntity.setProperty("genre_bit", movie.getGenreBit());
		movieEntity.setProperty("overview", movie.getOverview());
		movieEntity.setProperty("rating", movie.getRating());
		movieEntity.setProperty("popularity", movie.getPopularity());
		dataStore.put(movieEntity);
	}

	/**
	 * Retrieve InSing movies from the datastore [InSing_Movie] with title
	 * sorted in ascending order.
	 * 
	 * @param nothing
	 * @return an arraylist of InSing movies from the datastore [InSing_Movie]
	 */
	public static ArrayList<InSingMovie> retrieveMovieList() {
		DatastoreService dataStore = DatastoreServiceFactory.getDatastoreService();

		Query q = new Query(Constant.DS_TABLE_INSING_MOVIE_NOW_SHOWING).addSort("title");
		PreparedQuery pq = dataStore.prepare(q);

		List<Entity> movieEntityList = pq.asList(FetchOptions.Builder.withDefaults());

		ArrayList<InSingMovie> movieList = new ArrayList<InSingMovie>();
		for (Entity movieEntity : movieEntityList) {
			if (movieEntity.getProperty("tmdb_id") != null
					&& movieEntity.getProperty("tmdb_id").toString().length() > 0) {
				movieList.add(new InSingMovie(movieEntity.getKey().getName(),
						movieEntity.getProperty("title").toString(), movieEntity.getProperty("title2").toString(),
						movieEntity.getProperty("image_url").toString(), movieEntity.getProperty("tmdb_id").toString(),
						movieEntity.getProperty("genre_bit").toString(), movieEntity.getProperty("overview").toString(),
						Double.parseDouble(movieEntity.getProperty("rating").toString())));
			}
		}

		return movieList;
	}

	/**
	 * Retrieve InSing movies from the datastore [InSing_Movie] with title
	 * sorted in ascending order.
	 * 
	 * @param nothing
	 * @return an arraylist of InSing movies from the datastore [InSing_Movie]
	 */
	public static ArrayList<InSingMovie> top5RatedMovieList() {
		DatastoreService dataStore = DatastoreServiceFactory.getDatastoreService();

		Query q = new Query(Constant.DS_TABLE_INSING_MOVIE_NOW_SHOWING).addSort("popularity", SortDirection.DESCENDING);
		PreparedQuery pq = dataStore.prepare(q);

		List<Entity> movieEntityList = pq.asList(FetchOptions.Builder.withLimit(5));

		ArrayList<InSingMovie> movieList = new ArrayList<InSingMovie>();
		for (Entity movieEntity : movieEntityList) {
			if (movieEntity.getProperty("tmdb_id") != null
					&& movieEntity.getProperty("tmdb_id").toString().length() > 0) {
				System.out.println(movieEntity.getProperty("title").toString() + " "
						+ movieEntity.getProperty("popularity").toString());
				movieList.add(new InSingMovie(movieEntity.getKey().getName(),
						movieEntity.getProperty("title").toString(), movieEntity.getProperty("title2").toString(),
						movieEntity.getProperty("image_url").toString(), movieEntity.getProperty("tmdb_id").toString(),
						movieEntity.getProperty("genre_bit").toString(), movieEntity.getProperty("overview").toString(),
						Double.parseDouble(movieEntity.getProperty("rating").toString())));
			}
		}

		return movieList;
	}

	/**
	 * Retrieve InSing movies keys only from the datastore [InSing_Movie].
	 * 
	 * @param nothing
	 * @return a list of Entity from the datastore [InSing_Movie]
	 */
	public static List<Entity> retrieveMovieListKeysOnly() {
		DatastoreService dataStore = DatastoreServiceFactory.getDatastoreService();

		Query q = new Query(Constant.DS_TABLE_INSING_MOVIE_NOW_SHOWING).setKeysOnly();
		PreparedQuery pq = dataStore.prepare(q);

		List<Entity> movieEntityList = pq.asList(FetchOptions.Builder.withDefaults());

		return movieEntityList;
	}

	/**
	 * Remove non-existed InSing movies from the datastore. The removeList
	 * argument must specify the list of keys to be removed.
	 * 
	 * @param removeList
	 *            a list of keys to be removed from the datastore
	 * @return nothing
	 */
	public static void removeMovieList(ArrayList<String> removeList) {
		DatastoreService dataStore = DatastoreServiceFactory.getDatastoreService();

		for (String key : removeList) {
			dataStore.delete(KeyFactory.createKey(Constant.DS_TABLE_INSING_MOVIE_NOW_SHOWING, key));
		}
	}
}