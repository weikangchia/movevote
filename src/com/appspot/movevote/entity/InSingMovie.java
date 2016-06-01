package com.appspot.movevote.entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import org.jsoup.Jsoup;
import org.apache.commons.lang3.StringEscapeUtils;
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
	private static final Logger log = Logger.getLogger(InSingMovie.class.getName());

	private String tmdbId;

	public InSingMovie(String id) {
		super(id);
	}

	public InSingMovie(String id, String title, String imageUrl) {
		super(id, title, imageUrl);
		this.setTmdbId("");
	}

	public InSingMovie(String id, String title, String imageUrl, String tmdbID) {
		super(id, title, imageUrl);
		this.setTmdbId(tmdbID);
	}

	public String getTmdbId() {
		return tmdbId;
	}

	public void setTmdbId(String tmdbId) {
		this.tmdbId = tmdbId;
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
			Response mainResponse = Jsoup.connect(Constant.INSING_HOSTNAME + "movies/").execute();
			if (mainResponse.statusCode() == Internet.SUCCESS) {
				log.info("Connected successfully to " + Constant.INSING_HOSTNAME + "movies/");

				Document doc = mainResponse.parse();
				Elements movieListElements = doc.select("div[class^=movie-slideshow]").select("li");

				for (Element movieElement : movieListElements) {
					String[] splitInSingIdArr = movieElement.select("figure").select("a")
							.attr("href").split("/");
					System.out.println(splitInSingIdArr[3]);
					String inSingId = splitInSingIdArr[3].substring(3,
							splitInSingIdArr[3].length());
					String title = movieElement.select("figure").select("a").attr("title");

					try {
						Response detailResponse = Jsoup.connect(Constant.INSING_HOSTNAME + "movies/"
								+ splitInSingIdArr[2] + "/" + splitInSingIdArr[3] + "/showtimes")
								.execute();

						if (detailResponse.statusCode() == Internet.SUCCESS) {
							Document doc2 = detailResponse.parse();
							Element movieImageElement = doc2.select("figure[class^=thumbnail")
									.select("a").select("img").first();

							String tmdbId = TMDBMovie.retrieveTMDBIdByQuery(title);
							String imageUrl = movieImageElement.attr("src");

							if (tmdbId != null) {
								movieMap.put(inSingId,
										new InSingMovie(inSingId, title, imageUrl, tmdbId));
							} else {
								movieMap.put(inSingId, new InSingMovie(inSingId, title, imageUrl));
							}
						} else {
							System.out.println("error");
						}
					} catch (Exception ex2) {
						log.warning("Unable to retrieve inSing movie id: " + inSingId);
					}
				}
			} else {
				log.warning("Unable to connect to " + Constant.INSING_HOSTNAME + "movies/");
			}
		} catch (Exception ex) {
			log.warning("Unable to parse data from " + Constant.INSING_HOSTNAME + "movies/");
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
		List<Entity> currMovieEntityList = retrieveMovieListKeysOnly();

		// compare the current movie list with the movie list fetched from
		// InSing and add any difference into the removeList
		for (Entity movieEntity : currMovieEntityList) {
			String movieKey = movieEntity.getKey().getName();

			if (!movieMap.containsKey(movieKey)) {
				removeList.add(movieKey);
			}
		}

		// remove non-existing movie
		removeMovieList(removeList);

		// add new movie list into the datastore
		DatastoreService dataStore = DatastoreServiceFactory.getDatastoreService();
		for (Map.Entry<String, InSingMovie> entry : movieMap.entrySet()) {
			InSingMovie newMovie = entry.getValue();
			Entity movieEntity = new Entity("InSing_Movie", newMovie.getId());
			movieEntity.setProperty("title", StringEscapeUtils.unescapeHtml4(newMovie.getTitle()));
			movieEntity.setProperty("imageUrl", newMovie.getImageUrl());
			movieEntity.setProperty("tmdbId", newMovie.getTmdbId());
			dataStore.put(movieEntity);
		}

		// debug purpose
		System.out.println("Curr size: " + currMovieEntityList.size());
		System.out.println("New size: " + movieMap.size());
		System.out.println("Remove size: " + removeList.size());

		log.info(movieMap.size() + " new movies are stored into the datastore.");
	}

	/**
	 * Retrieve InSing movies keys only from the datastore [InSing_Movie].
	 * 
	 * @param nothing
	 * @return a list of Entity from the datastore [InSing_Movie]
	 */
	public static List<Entity> retrieveMovieListKeysOnly() {
		DatastoreService dataStore = DatastoreServiceFactory.getDatastoreService();

		Query q = new Query("InSing_Movie").setKeysOnly();
		PreparedQuery pq = dataStore.prepare(q);

		List<Entity> movieEntityList = pq.asList(FetchOptions.Builder.withDefaults());

		return movieEntityList;
	}

	/**
	 * Retrieve InSing movies from the datastore [InSing_Movie] with title
	 * sorted in ascending order.
	 * 
	 * @param nothing
	 * @return a arraylist of InSing movies from the datastore [InSing_Movie]
	 */
	public static ArrayList<InSingMovie> retrieveMovieList() {
		DatastoreService dataStore = DatastoreServiceFactory.getDatastoreService();

		Query q = new Query("InSing_Movie").addSort("title");
		PreparedQuery pq = dataStore.prepare(q);

		List<Entity> movieEntityList = pq.asList(FetchOptions.Builder.withDefaults());

		ArrayList<InSingMovie> movieList = new ArrayList<InSingMovie>();
		for (Entity movieEntity : movieEntityList) {
			if (movieEntity.getProperty("tmdbId") != null
					&& movieEntity.getProperty("tmdbId").toString().length() > 0) {
				movieList.add(new InSingMovie(movieEntity.getKey().getName(),
						movieEntity.getProperty("title").toString(),
						movieEntity.getProperty("imageUrl").toString(),
						movieEntity.getProperty("tmdbId").toString()));
			}
		}

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
		DatastoreService dataStore = DatastoreServiceFactory.getDatastoreService();

		for (String key : removeList) {
			dataStore.delete(KeyFactory.createKey("InSing_Movie", key));
		}
	}
}