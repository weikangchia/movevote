package com.appspot.movevote.entity;

import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import org.apache.commons.lang3.StringEscapeUtils;
import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.appspot.movevote.db.InSingMovieDB;
import com.appspot.movevote.db.TMDBMovieDB;
import com.google.appengine.api.datastore.Entity;

public class InSingMovie extends Movie {
	private static final Logger log = Logger.getLogger(InSingMovie.class.getName());

	private String tmdbId;
	private String title2;
	private String genreBit;
	private String overview;

	public InSingMovie(String id) {
		super(id);
	}

	public InSingMovie(String id, String title, String title2, String imageUrl) {
		super(id, title, imageUrl);
		this.setTmdbId(null);
		this.title2 = title2;
	}

	public InSingMovie(String id, String title, String title2, String imageUrl, String tmdbID,
			String genreBit, String overview) {
		super(id, title, imageUrl);
		this.setTmdbId(tmdbID);
		this.title2 = title2;
		this.genreBit = genreBit;
		this.overview = overview;
	}

	public String getTmdbId() {
		return tmdbId;
	}

	public void setTmdbId(String tmdbId) {
		this.tmdbId = tmdbId;
	}

	public String getTitle2() {
		return title2;
	}

	public void setTitle2(String title2) {
		this.title2 = title2;
	}

	public String getGenreBit() {
		return genreBit;
	}

	public void setGenreBit(String genreBit) {
		this.genreBit = genreBit;
	}

	public String getOverview() {
		return overview;
	}

	public void setOverview(String overview) {
		this.overview = overview;
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
			if (mainResponse.statusCode() == HttpURLConnection.HTTP_OK) {
				log.info("Connected successfully to " + Constant.INSING_HOSTNAME + "movies/");

				Document doc = mainResponse.parse();
				Elements movieListElements = doc.select("div[class^=movie-slideshow]").select("li");

				for (Element movieElement : movieListElements) {
					String[] splitInSingIdArr = movieElement.select("figure").select("a")
							.attr("href").split("/");
					String inSingId = splitInSingIdArr[3].substring(3,
							splitInSingIdArr[3].length());
					String title = movieElement.select("figure").select("a").attr("title");
					String title2 = splitInSingIdArr[2];
					try {
						Response detailResponse = Jsoup.connect(Constant.INSING_HOSTNAME + "movies/"
								+ splitInSingIdArr[2] + "/" + splitInSingIdArr[3] + "/showtimes")
								.execute();

						if (detailResponse.statusCode() == HttpURLConnection.HTTP_OK) {
							Document doc2 = detailResponse.parse();
							Element movieImageElement = doc2.select("figure[class^=thumbnail")
									.select("a").select("img").first();

							String imageUrl = movieImageElement.attr("src");
							movieMap.put(inSingId, new InSingMovie(inSingId,
									StringEscapeUtils.unescapeHtml4(title), title2, imageUrl));
						} else {
							log.info("Unable to connect to " + Constant.INSING_HOSTNAME + "movies/"
									+ splitInSingIdArr[2] + "/" + splitInSingIdArr[3]
									+ "/showtimes");
						}
					} catch (Exception ex2) {
						log.info("Error while trying to parse data from " + Constant.INSING_HOSTNAME
								+ "movies/" + splitInSingIdArr[2] + "/" + splitInSingIdArr[3]
								+ "/showtimes");
						System.out.println(ex2);
					}
				}
			} else {
				log.warning("Unable to connect to " + Constant.INSING_HOSTNAME + "movies/");
			}
		} catch (Exception ex) {
			log.warning("Error while trying to parse data from " + Constant.INSING_HOSTNAME
					+ "movies/");
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
		List<Entity> currMovieEntityList = InSingMovieDB.retrieveMovieListKeysOnly();

		// compare the current movie list with the movie list fetched from
		// InSing and add any difference into the removeList
		for (Entity movieEntity : currMovieEntityList) {
			String movieKey = movieEntity.getKey().getName();

			if (!movieMap.containsKey(movieKey)) {
				removeList.add(movieKey);
			}
		}

		// remove non-existing movie
		InSingMovieDB.removeMovieList(removeList);

		// add new movie list into the datastore
		for (Map.Entry<String, InSingMovie> entry : movieMap.entrySet()) {
			InSingMovie newInSingMovie = entry.getValue();

			TMDBMovie tmdbMovie = TMDBMovie.searchTMDBByTitle(newInSingMovie.getTitle());
			// if tmdbId is null, don't store it into the tmdb movie table
			if (tmdbMovie != null) {
				TMDBMovieDB.storeMovie(tmdbMovie);

				newInSingMovie.setTmdbId(tmdbMovie.getId());
				newInSingMovie.setGenreBit(tmdbMovie.getGenreBit());
				newInSingMovie.setOverview(tmdbMovie.getOverview());
				
				InSingMovieDB.storeInSingMovie(newInSingMovie);
			}
		}

		log.info(movieMap.size() + " new movies are added into the datastore and "
				+ removeList.size() + " old movies are removed from the datastore.");
	}

	public static HashMap<InSingMovieShowPlace, HashMap<String, ArrayList<InSingMovieShowTime>>> retrieveShowTime(
			String id, String title2, String date) {
		HashMap<InSingMovieShowPlace, HashMap<String, ArrayList<InSingMovieShowTime>>> showPlaceHashMap = new HashMap<InSingMovieShowPlace, HashMap<String, ArrayList<InSingMovieShowTime>>>();

		try {
			String showUrl = "http://www.insing.com/movies/" + title2 + "/id-" + id + "/showtimes/";
			if (date != null) {
				showUrl = "http://www.insing.com/movies/" + title2 + "/id-" + id + "/showtimes/?d="
						+ date;
			}

			Response response = Jsoup.connect(showUrl).execute();
			if (response.statusCode() == HttpURLConnection.HTTP_OK) {
				Document doc = response.parse();
				Elements cinemaElementList = doc.select("article[class^=cinema-showtime]");

				for (Element cinemaElement : cinemaElementList) {
					String cinemaName = cinemaElement.select("div[class^=cinemas-name]").html();
					String address = "Singapore";

					HashMap<String, ArrayList<InSingMovieShowTime>> showTimeHashMap = new HashMap<String, ArrayList<InSingMovieShowTime>>();

					// get showtimes list
					Elements showTimesElementList = cinemaElement
							.select("li[class^=showtimes slot-time-format");
					for (Element showTimeElement : showTimesElementList) {
						String format = showTimeElement.attr("format");
						String timing = showTimeElement.select("a").html();
						String url = showTimeElement.select("a").attr("onclick");

						if (cinemaName.startsWith("GV")) {
							url = "http://www.gv.com.sg/";
						} else {
							int startIndex = url.indexOf("this.href='");
							url = url.substring(startIndex + 11, url.length() - 1);
						}

						if (!showTimeHashMap.containsKey(format)) {
							showTimeHashMap.put(format, new ArrayList<InSingMovieShowTime>());
						}

						((ArrayList<InSingMovieShowTime>) showTimeHashMap.get(format))
								.add(new InSingMovieShowTime(format, url, timing));
					}

					showPlaceHashMap.put(new InSingMovieShowPlace(cinemaName, address),
							showTimeHashMap);
				}
			} else {
				System.out.println("unable to connect");
			}
		} catch (Exception ex) {
			System.out.println("unable to parse data");
		}

		return showPlaceHashMap;
	}
}