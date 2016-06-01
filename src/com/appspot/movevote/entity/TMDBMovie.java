package com.appspot.movevote.entity;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.logging.Logger;

import org.apache.commons.lang3.StringEscapeUtils;

import com.appspot.movevote.helper.TMDBHelper;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class TMDBMovie extends Movie {
	private static final Logger log = Logger.getLogger(TMDBMovie.class.getName());
	private static final int INIT_CREW_CAPACITY = 30;

	private String tagLine;
	private String overview;
	private String releaseDate;
	private ArrayList<Genre> genreList;
	private double rating;
	private int duration;
	private ArrayList<Cast> castList;
	private HashMap<String, ArrayList<Crew>> crewMapList;
	private ArrayList<YouTubeVideo> youTubeVideoList;
	private ArrayList<TMDBMovie> similarList;
	private ArrayList<Review> reviewList;

	public TMDBMovie(String id) {
		super(id);
		genreList = new ArrayList<Genre>();
		castList = new ArrayList<Cast>();
		setCrewMapList(new HashMap<String, ArrayList<Crew>>(INIT_CREW_CAPACITY));
		youTubeVideoList = new ArrayList<YouTubeVideo>();
		similarList = new ArrayList<TMDBMovie>();
		setReviewList(new ArrayList<Review>());
	}

	public TMDBMovie(String id, String title, String imageUrl) {
		super(id, title, imageUrl);
	}

	public String getOverview() {
		return overview;
	}

	public void setOverview(String overview) {
		this.overview = overview;
	}

	public String getReleaseDate() {
		return releaseDate;
	}

	public void setReleaseDate(String releaseDate) {
		this.releaseDate = releaseDate;
	}

	public String getTagLine() {
		return tagLine;
	}

	public void setTagLine(String tagLine) {
		this.tagLine = tagLine;
	}

	public ArrayList<Genre> getGenreList() {
		return genreList;
	}

	public void setGenreList(ArrayList<Genre> genreList) {
		this.genreList = genreList;
	}

	public double getRating() {
		return rating;
	}

	public void setRating(double rating) {
		this.rating = rating;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public ArrayList<Cast> getCastList() {
		return castList;
	}

	public void setCastList(ArrayList<Cast> castList) {
		this.castList = castList;
	}

	public HashMap<String, ArrayList<Crew>> getCrewMapList() {
		return crewMapList;
	}

	public void setCrewMapList(HashMap<String, ArrayList<Crew>> crewMapList) {
		this.crewMapList = crewMapList;
	}

	public ArrayList<YouTubeVideo> getYouTubeVideoList() {
		return youTubeVideoList;
	}

	public void setYouTubeVideoList(ArrayList<YouTubeVideo> youTubeVideoList) {
		this.youTubeVideoList = youTubeVideoList;
	}

	public ArrayList<TMDBMovie> getSimilarList() {
		return similarList;
	}

	public void setSimilarList(ArrayList<TMDBMovie> similarList) {
		this.similarList = similarList;
	}

	public ArrayList<Review> getReviewList() {
		return reviewList;
	}

	public void setReviewList(ArrayList<Review> reviewList) {
		this.reviewList = reviewList;
	}

	/**
	 * Retrieve TMDB id based on a movie title
	 * 
	 * @param query
	 *            movie title
	 * @return id: tmdbId
	 */
	public static String retrieveTMDBIdByQuery(String query) {
		String id = null;

		try {
			String urlStr = Constant.TMDB_HOSTNAME + "search/movie?api_key=" + Constant.TMDB_API_KEY
					+ "&query=" + URLEncoder.encode(query, "UTF-8") + "&year="
					+ Calendar.getInstance().get(Calendar.YEAR);

			URL url = new URL(urlStr);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("GET");
			connection.connect();

			if (connection.getResponseCode() == Internet.SUCCESS) {
				BufferedReader br = new BufferedReader(
						new InputStreamReader(connection.getInputStream(), "UTF-8"));
				String jsonLine = br.readLine();

				ObjectMapper mapper = new ObjectMapper();
				JsonNode parentNode = mapper.readTree(jsonLine);
				JsonNode resultNode = parentNode.findPath("results");

				if (resultNode.size() > 0) {
					id = resultNode.get(0).get("id").asText();
				}
			}
		} catch (Exception ex) {
			log.warning("Unable to retrieve tmdb id for " + query);
		}

		return id;
	}

	/**
	 * Retrieve TMDB id based on a tmdb id
	 * 
	 * @param id
	 *            tmdb id
	 * @return TMDBMovie object
	 */
	public static TMDBMovie retrieveTMDBIdById(String id) {
		TMDBMovie movie = new TMDBMovie(id);

		// retrieve main movie details
		try {
			String urlStr = Constant.TMDB_HOSTNAME + "movie/" + id + "?api_key="
					+ Constant.TMDB_API_KEY;

			System.out.println(urlStr);

			URL url = new URL(urlStr);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("GET");
			connection.connect();

			if (connection.getResponseCode() == Internet.SUCCESS) {
				BufferedReader br = new BufferedReader(
						new InputStreamReader(connection.getInputStream(), "UTF-8"));
				String jsonLine = br.readLine();

				ObjectMapper mapper = new ObjectMapper();
				JsonNode parentNode = mapper.readTree(jsonLine);

				movie.setTitle(parentNode.get("original_title").asText());
				movie.setOverview(
						StringEscapeUtils.unescapeHtml4(parentNode.get("overview").asText()));

				System.out.println(parentNode.get("poster_path").toString());
				// if poster_path is empty we will set no-poster image
				if (parentNode.get("poster_path") == null
						|| parentNode.get("poster_path").toString().length() == 0
						|| parentNode.get("poster_path").toString().equals("null")) {
					movie.setImageUrl("/assets/img/no-poster.png");
				} else {
					movie.setImageUrl(
							TMDBHelper.getAbsImageUrl(parentNode.get("poster_path").asText(),
									Constant.TMDB_IMAGE_POSTER_SIZE));
				}

				movie.setReleaseDate(parentNode.get("release_date").asText());
				movie.setTagLine(parentNode.get("tagline").asText());
				movie.setDuration(parentNode.get("runtime").asInt());
				movie.setRating(parentNode.get("vote_average").asDouble());

				// retrieve genre list
				JsonNode genreNodes = parentNode.path("genres");
				for (int i = 0; i < genreNodes.size(); i++) {
					movie.genreList.add(new Genre(genreNodes.get(i).get("id").asInt(),
							genreNodes.get(i).get("name").asText()));
				}
			}
		} catch (Exception ex) {
			log.warning("Unable to retrieve tmdb movie main details for " + id);
		}

		// retrieve credits details
		try {
			String urlStr = Constant.TMDB_HOSTNAME + "movie/" + id + "/credits?api_key="
					+ Constant.TMDB_API_KEY;

			System.out.println(urlStr);

			URL url = new URL(urlStr);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("GET");
			connection.connect();

			if (connection.getResponseCode() == Internet.SUCCESS) {
				BufferedReader br = new BufferedReader(
						new InputStreamReader(connection.getInputStream(), "UTF-8"));
				String jsonLine = br.readLine();

				ObjectMapper mapper = new ObjectMapper();
				JsonNode parentNode = mapper.readTree(jsonLine);

				// retrieve cast list
				JsonNode castNodes = parentNode.path("cast");
				for (int i = 0; i < castNodes.size(); i++) {
					movie.castList.add(new Cast(castNodes.get(i).get("id").asText(),
							castNodes.get(i).get("name").asText(),
							castNodes.get(i).get("profile_path").asText(),
							castNodes.get(i).get("character").asText()));
				}

				// retrieve crew list
				JsonNode crewNodes = parentNode.path("crew");
				for (int i = 0; i < crewNodes.size(); i++) {
					String job = crewNodes.get(i).get("job").asText();
					if (movie.crewMapList.containsKey(job)) {
						movie.crewMapList.get(job)
								.add(new Crew(crewNodes.get(i).get("id").asText(),
										crewNodes.get(i).get("name").asText(),
										crewNodes.get(i).get("profile_path").asText(),
										crewNodes.get(i).get("department").asText(), job));
					} else {
						ArrayList<Crew> newCrewList = new ArrayList<Crew>();
						newCrewList.add(new Crew(crewNodes.get(i).get("id").asText(),
								crewNodes.get(i).get("name").asText(),
								crewNodes.get(i).get("profile_path").asText(),
								crewNodes.get(i).get("department").asText(), job));
						movie.crewMapList.put(job, newCrewList);
					}
				}
			}
		} catch (Exception ex) {
			log.warning("Unable to retrieve tmdb movie credits details for " + id);
		}

		// retrieve video details
		try {
			String urlStr = Constant.TMDB_HOSTNAME + "movie/" + id + "/videos?api_key="
					+ Constant.TMDB_API_KEY;

			System.out.println(urlStr);

			URL url = new URL(urlStr);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("GET");
			connection.connect();

			if (connection.getResponseCode() == Internet.SUCCESS) {
				BufferedReader br = new BufferedReader(
						new InputStreamReader(connection.getInputStream(), "UTF-8"));
				String jsonLine = br.readLine();

				ObjectMapper mapper = new ObjectMapper();
				JsonNode parentNode = mapper.readTree(jsonLine);

				// retrieve cast list
				JsonNode videoNodes = parentNode.path("results");
				for (int i = 0; i < videoNodes.size(); i++) {
					movie.youTubeVideoList
							.add(new YouTubeVideo(videoNodes.get(i).get("key").asText(),
									videoNodes.get(i).get("name").asText()));
				}
			}
		} catch (Exception ex) {
			log.warning("Unable to retrieve tmdb movie videos details for " + id);
		}

		// retrieve similar movies
		try {
			String urlStr = Constant.TMDB_HOSTNAME + "movie/" + id + "/similar?api_key="
					+ Constant.TMDB_API_KEY;

			System.out.println(urlStr);

			URL url = new URL(urlStr);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("GET");
			connection.connect();

			if (connection.getResponseCode() == Internet.SUCCESS) {
				BufferedReader br = new BufferedReader(
						new InputStreamReader(connection.getInputStream(), "UTF-8"));
				String jsonLine = br.readLine();

				ObjectMapper mapper = new ObjectMapper();
				JsonNode parentNode = mapper.readTree(jsonLine);

				// retrieve movie details
				JsonNode resultNodes = parentNode.path("results");
				for (int i = 0; i < resultNodes.size(); i++) {
					TMDBMovie similarMovie = new TMDBMovie(resultNodes.get(i).get("id").asText(),
							resultNodes.get(i).get("title").asText(),
							TMDBHelper.getAbsImageUrl(
									resultNodes.get(i).get("poster_path").asText(),
									Constant.TMDB_IMAGE_POSTER_SIZE));
					similarMovie.setOverview(resultNodes.get(i).get("overview").asText());
					movie.similarList.add(similarMovie);
				}
			}
		} catch (Exception ex) {
			log.warning("Unable to retrieve tmdb similar movie details for " + id);
		}

		// retrieve reviews
		try {
			String urlStr = Constant.TMDB_HOSTNAME + "movie/" + id + "/reviews?api_key="
					+ Constant.TMDB_API_KEY;

			System.out.println(urlStr);

			URL url = new URL(urlStr);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("GET");
			connection.connect();

			if (connection.getResponseCode() == Internet.SUCCESS) {
				BufferedReader br = new BufferedReader(
						new InputStreamReader(connection.getInputStream(), "UTF-8"));
				String jsonLine = br.readLine();

				ObjectMapper mapper = new ObjectMapper();
				JsonNode parentNode = mapper.readTree(jsonLine);

				// retrieve review details
				JsonNode resultNodes = parentNode.path("results");
				for (int i = 0; i < resultNodes.size(); i++) {
					movie.reviewList.add(
							new Review(resultNodes.get(i).get("author").asText(), resultNodes.get(i)
									.get("content").asText().replaceAll("\r\n\r\n", "<br/><br/>")));
				}
			}
		} catch (Exception ex) {
			log.warning("Unable to retrieve tmdb movie review details for " + id);
		}

		return movie;
	}
}