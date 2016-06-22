package com.appspot.movevote.db;

import java.util.Date;

import com.appspot.movevote.entity.Constant;
import com.appspot.movevote.entity.TMDBMovie;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;

public class TMDBMovieDB {
	public static void storeMovie(TMDBMovie movie) {
		DatastoreService dataStore = DatastoreServiceFactory.getDatastoreService();
		Entity movieEntity = new Entity(Constant.DS_TABLE_TMDB_MOVIE, movie.getId());
		movieEntity.setProperty("title", movie.getTitle());
		movieEntity.setProperty("last_update", new Date());
		movieEntity.setProperty("overview", movie.getOverview());
		movieEntity.setProperty("poster_path", movie.getImageUrl());
		movieEntity.setProperty("backdrop_path", movie.getImageBackUrl());
		movieEntity.setProperty("release_date", movie.getReleaseDate());
		movieEntity.setProperty("vote_average", movie.getRating());
		movieEntity.setProperty("genre_bit", movie.getGenreBit());

		dataStore.put(movieEntity);
	}
}