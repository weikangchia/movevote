package com.appspot.movevote.entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.Connection.Response;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;

public class InSingMovie extends Movie {
	public InSingMovie(String id) {
		super(id);
	}

	public InSingMovie(String id, String title, String imageUrl) {
		super(id, title, imageUrl);
	}

	public boolean equals(Object obj) {
		if (obj != null && obj instanceof InSingMovie) {
			InSingMovie movie = (InSingMovie) obj;
			if (this.getId().equals(movie.getId())) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Fetch a new list of movies from InSing.
	 * 
	 * @param nothing
	 * @return movieMap a HashMap; key: movieId and value: InSingMovie
	 */
	public static HashMap<String, InSingMovie> fetchFromInSing() {
		HashMap<String, InSingMovie> movieMap = new HashMap<String, InSingMovie>();

		try {
			Response mainResponse = Jsoup.connect(
					Constant.INSING_HOSTNAME + "movies/").execute();
			if (mainResponse.statusCode() == Internet.SUCCESS) {
				Document doc = mainResponse.parse();
				Elements movieListElements = doc.select("ul[class^=movie-id]")
						.select("li");

				for (Element movieElement : movieListElements) {
					if (!movieElement.html().equals("Search for a movie")) {
						Response detailResponse = Jsoup.connect(
								Constant.INSING_HOSTNAME + "movies/"
										+ movieElement.attr("data-slug")
										+ "/id-"
										+ movieElement.attr("data-value")
										+ "/showtimes").execute();

						if (detailResponse.statusCode() == Internet.SUCCESS) {
							Document doc2 = detailResponse.parse();
							Element movieImageElement = doc2
									.select("figure[class^=thumbnail")
									.select("a").select("img").first();

							System.out.println(movieImageElement.attr("src"));

							movieMap.put(
									movieElement.attr("data-value"),
									new InSingMovie(movieElement
											.attr("data-value"), movieElement
											.html(), movieImageElement
											.attr("src")));
						}
					}
				}
			} else {
				System.out.println("unable to connect to "
						+ Constant.INSING_HOSTNAME);
			}
		} catch (Exception ex) {
			System.out.println("unable to parse data");
		}
		return movieMap;
	}

	/**
	 * Store the movieMap into the datastore and non-existed movies in the
	 * datastore will be removed.
	 * 
	 * @param movieMap
	 *            HashMap that contains all the movies that are going to be
	 *            stored
	 * @return nothing
	 */
	public static void storeMovie(HashMap<String, InSingMovie> movieMap) {
		ArrayList<String> removeList = new ArrayList<String>();

		// retrieve the current movie list stored in datastore
		List<Entity> currList = retrieveMovieList();

		// compare the current movie list with the movie list fetched from
		// InSing
		// and add any difference into the removeList
		for (Entity movieEntity : currList) {
			String movieKey = movieEntity.getKey().getName();

			if (!movieMap.containsKey(movieKey)) {
				removeList.add(movieKey);
			}
		}

		// remove non-existing movie
		removeMovieList(removeList);

		// add new movie list into the datastore
		DatastoreService dataStore = DatastoreServiceFactory
				.getDatastoreService();
		for (Map.Entry<String, InSingMovie> entry : movieMap.entrySet()) {
			InSingMovie newMovie = entry.getValue();
			Entity movieEntity = new Entity("InSing_Movie", newMovie.getId());
			movieEntity.setProperty("title", newMovie.getTitle());
			movieEntity.setProperty("imageUrl", newMovie.getImageUrl());
			dataStore.put(movieEntity);
		}

		// debug purpose
		System.out.println("Curr size: " + currList.size());
		System.out.println("New size: " + movieMap.size());
		System.out.println("Remove size: " + removeList.size());
	}

	/**
	 * Retrieve InSing movies from the datastore [InSing_Movie].
	 * 
	 * @param nothing
	 * @return a list of Entity from the datastore [InSing_Movie]
	 */
	public static List<Entity> retrieveMovieList() {
		DatastoreService dataStore = DatastoreServiceFactory
				.getDatastoreService();

		Query q = new Query("InSing_Movie").setKeysOnly();
		PreparedQuery pq = dataStore.prepare(q);

		List<Entity> movieList = pq.asList(FetchOptions.Builder.withDefaults());

		return movieList;
	}

	/**
	 * Remove non-existed InSing movies from the datastore. The removeList
	 * argument must specify the list of keys to be removed.
	 * 
	 * @param removeList
	 *            a list of keys to be removed from the datastore
	 * @return nothing
	 */
	private static void removeMovieList(ArrayList<String> removeList) {
		DatastoreService dataStore = DatastoreServiceFactory
				.getDatastoreService();

		for (String key : removeList) {
			dataStore.delete(KeyFactory.createKey("InSing_Movie", key));
		}
	}
}