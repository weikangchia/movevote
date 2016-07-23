package com.appspot.movevote.entity;

import java.io.IOException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.logging.Logger;

import org.apache.commons.lang3.StringEscapeUtils;

import com.appspot.movevote.helper.InternetHelper;
import com.appspot.movevote.helper.TMDBHelper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class TMDBMovie extends Movie {
	private static final Logger log = Logger.getLogger(TMDBMovie.class.getName());

	private String overview;
	private String releaseDate;
	private ArrayList<Genre> genreList;
	private String genreBit;
	private double rating;
	private int duration;
	private ArrayList<Cast> castList;
	private HashMap<String, ArrayList<Crew>> crewMapList;
	private ArrayList<YouTubeVideo> youTubeVideoList;
	private ArrayList<TMDBMovie> similarList;
	private String imageBackUrl;
	private double popularity;

	public TMDBMovie(String id) {
		super(id);
		genreList = new ArrayList<Genre>();
		castList = new ArrayList<Cast>();
		setCrewMapList(new HashMap<String, ArrayList<Crew>>());
		youTubeVideoList = new ArrayList<YouTubeVideo>();
		similarList = new ArrayList<TMDBMovie>();
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

	public String getImageBackUrl() {
		return imageBackUrl;
	}

	public void setImageBackUrl(String imageBackUrl) {
		this.imageBackUrl = imageBackUrl;
	}

	public String getGenreBit() {
		return genreBit;
	}

	public void setGenreBit(String genreBit) {
		this.genreBit = genreBit;
	}

	public double getPopularity() {
		return popularity;
	}

	public void setPopularity(double popularity) {
		this.popularity = popularity;
	}

	/**
	 * Search for TMDB movie based on movie title
	 * 
	 * @param title
	 *            movie title
	 * @return tmdbmovie object
	 */
	public static TMDBMovie searchTMDBByTitle(String title) {
		try {
			String url = Constant.TMDB_HOSTNAME + "search/movie?api_key=" + Constant.TMDB_API_KEY + "&query="
					+ URLEncoder.encode(title, "UTF-8") + "&year=" + Calendar.getInstance().get(Calendar.YEAR);

			InternetHelper internetHelper = new InternetHelper(url);
			String json = internetHelper.getHtmlData();

			if (json != null) {
				ObjectMapper mapper = new ObjectMapper();
				JsonNode parentNode = mapper.readTree(json);
				JsonNode resultNode = parentNode.findPath("results");

				if (resultNode.size() > 0) {
					JsonNode movieNode = resultNode.get(0);

					TMDBMovie movie = new TMDBMovie(movieNode.get("id").asText());

					if (movieNode.get("poster_path") == null || movieNode.get("poster_path").toString().length() == 0
							|| movieNode.get("poster_path").toString().equals("null")) {
						movie.setImageUrl("/assets/img/no-poster.png");
					} else {
						movie.setImageUrl(TMDBHelper.getAbsImageUrl(movieNode.get("poster_path").asText(),
								Constant.TMDB_IMAGE_POSTER_SIZE));
					}

					if (movieNode.get("backdrop_path") == null
							|| movieNode.get("backdrop_path").toString().length() == 0
							|| movieNode.get("backdrop_path").toString().equals("null")) {
						movie.setImageBackUrl("/assets/img/no-poster.png");
					} else {
						movie.setImageBackUrl(TMDBHelper.getAbsImageUrl(movieNode.get("backdrop_path").asText(),
								Constant.TMDB_IMAGE_POSTER_SIZE));
					}

					movie.setTitle(StringEscapeUtils.unescapeHtml4(movieNode.get("original_title").asText()));
					movie.setOverview(StringEscapeUtils.unescapeHtml4(movieNode.get("overview").asText()));
					movie.setReleaseDate(movieNode.get("release_date").asText());
					movie.setRating(movieNode.get("vote_average").asDouble());
					movie.setPopularity(movieNode.get("popularity").asDouble());

					// get the genres
					ArrayList<Integer> genreIdList = new ArrayList<Integer>();
					for (int g = 0; g < movieNode.get("genre_ids").size(); g++) {
						int genreId = movieNode.get("genre_ids").get(g).asInt();
						movie.getGenreList().add(new Genre(genreId, GenreEnum.getById(genreId).getName()));
						genreIdList.add(genreId);
					}

					movie.setGenreBit(genresBitBuilder(genreIdList));
					return movie;
				}
			}
		} catch (Exception ex) {
			log.warning("Unable to retrieve tmdb id for " + title);
		}
		return null;
	}

	/**
	 * Retrieve TMDB movie overview
	 */
	public void retrieveBasicDetails() {
		try {
			String url = Constant.TMDB_HOSTNAME + "movie/" + getId() + "?api_key=" + Constant.TMDB_API_KEY;

			InternetHelper internetHelper = new InternetHelper(url);
			String json = internetHelper.getHtmlData();

			if (json != null) {
				ObjectMapper mapper = new ObjectMapper();
				JsonNode parentNode = mapper.readTree(json);

				setTitle(StringEscapeUtils.unescapeHtml4(parentNode.get("original_title").asText()));
				setOverview(StringEscapeUtils.unescapeHtml4(parentNode.get("overview").asText()));

				// if poster_path is empty we will set no-poster image
				if (parentNode.get("poster_path") == null || parentNode.get("poster_path").toString().length() == 0
						|| parentNode.get("poster_path").toString().equals("null")) {
					setImageUrl("/assets/img/no-poster.png");
				} else {
					setImageUrl(TMDBHelper.getAbsImageUrl(parentNode.get("poster_path").asText(),
							Constant.TMDB_IMAGE_POSTER_SIZE));
				}

				if (parentNode.get("backdrop_path") == null || parentNode.get("backdrop_path").toString().length() == 0
						|| parentNode.get("backdrop_path").toString().equals("null")) {
					setImageBackUrl("/assets/img/no-poster.png");
				} else {
					setImageBackUrl(TMDBHelper.getAbsImageUrl(parentNode.get("backdrop_path").asText(),
							Constant.TMDB_IMAGE_POSTER_SIZE));
				}

				setReleaseDate(parentNode.get("release_date").asText());
				setDuration(parentNode.get("runtime").asInt());
				setRating(parentNode.get("vote_average").asDouble());

				// retrieve genre list
				ArrayList<Integer> genreIdList = new ArrayList<Integer>();
				JsonNode genreNodes = parentNode.path("genres");
				for (int i = 0; i < genreNodes.size(); i++) {
					int genreId = genreNodes.get(i).get("id").asInt();
					genreList.add(new Genre(genreId, genreNodes.get(i).get("name").asText()));
					genreIdList.add(genreId);
				}
				setGenreBit(genresBitBuilder(genreIdList));
			}
		} catch (Exception ex) {
			log.warning("Unable to retrieve tmdb movie main details for " + getId());
		}
	}

	/**
	 * Retrieve TMDB movie credits
	 */
	public void retrieveCredits() {
		try {
			String url = Constant.TMDB_HOSTNAME + "movie/" + getId() + "/credits?api_key=" + Constant.TMDB_API_KEY;

			InternetHelper internetHelper = new InternetHelper(url);
			String json = internetHelper.getHtmlData();

			if (json != null) {
				ObjectMapper mapper = new ObjectMapper();
				JsonNode parentNode = mapper.readTree(json);

				// retrieve cast list
				JsonNode castNodes = parentNode.path("cast");
				for (int i = 0; i < castNodes.size(); i++) {
					castList.add(new Cast(castNodes.get(i).get("id").asText(), castNodes.get(i).get("name").asText(),
							castNodes.get(i).get("profile_path").asText(), castNodes.get(i).get("character").asText()));
				}

				// retrieve crew list
				JsonNode crewNodes = parentNode.path("crew");
				for (int i = 0; i < crewNodes.size(); i++) {
					String job = crewNodes.get(i).get("job").asText();
					if (crewMapList.containsKey(job)) {
						crewMapList.get(job).add(new Crew(crewNodes.get(i).get("id").asText(),
								crewNodes.get(i).get("name").asText(), crewNodes.get(i).get("profile_path").asText(),
								crewNodes.get(i).get("department").asText(), job));
					} else {
						ArrayList<Crew> newCrewList = new ArrayList<Crew>();
						newCrewList.add(new Crew(crewNodes.get(i).get("id").asText(),
								crewNodes.get(i).get("name").asText(), crewNodes.get(i).get("profile_path").asText(),
								crewNodes.get(i).get("department").asText(), job));
						crewMapList.put(job, newCrewList);
					}
				}
			}
		} catch (Exception ex) {
			log.warning("Unable to retrieve tmdb movie credits details for " + getId());
		}
	}

	/**
	 * Retrieve TMDB movie videos
	 */
	public void retrieveVideo() {
		try {
			String url = Constant.TMDB_HOSTNAME + "movie/" + getId() + "/videos?api_key=" + Constant.TMDB_API_KEY;

			InternetHelper internetHelper = new InternetHelper(url);
			String json = internetHelper.getHtmlData();

			if (json != null) {
				ObjectMapper mapper = new ObjectMapper();
				JsonNode parentNode = mapper.readTree(json);
				JsonNode videoNodes = parentNode.path("results");
				for (int i = 0; i < videoNodes.size(); i++) {
					youTubeVideoList.add(new YouTubeVideo(videoNodes.get(i).get("key").asText(),
							videoNodes.get(i).get("name").asText()));
				}
			}
		} catch (Exception ex) {
			log.warning("Unable to retrieve tmdb movie videos details for " + getId());
		}
	}

	/**
	 * Retrieve TMDB movie similar movies
	 */
	public void retrieveSimilar() {
		try {
			String url = Constant.TMDB_HOSTNAME + "movie/" + getId() + "/similar?api_key=" + Constant.TMDB_API_KEY;

			InternetHelper internetHelper = new InternetHelper(url);
			String json = internetHelper.getHtmlData();

			if (json != null) {
				ObjectMapper mapper = new ObjectMapper();
				JsonNode parentNode = mapper.readTree(json);

				// retrieve movie details
				JsonNode resultNodes = parentNode.path("results");
				for (int i = 0; i < resultNodes.size(); i++) {
					TMDBMovie similarMovie = new TMDBMovie(resultNodes.get(i).get("id").asText(),
							resultNodes.get(i).get("title").asText(), TMDBHelper.getAbsImageUrl(
									resultNodes.get(i).get("poster_path").asText(), Constant.TMDB_IMAGE_POSTER_SIZE));
					similarMovie.setOverview(resultNodes.get(i).get("overview").asText());
					similarList.add(similarMovie);
				}
			}
		} catch (Exception ex) {
			log.warning("Unable to retrieve tmdb similar movie details for " + getId());
		}
	}

	public void retrieveAll() {
		retrieveBasicDetails();
		retrieveSimilar();
		retrieveVideo();
		retrieveCredits();
	}

	/**
	 * Retrieve discover list
	 * 
	 * @param page
	 *            page number
	 * @param voteAvg
	 *            vote average greater than or equal
	 * @param genres
	 *            id of the genres
	 * @return ArrayList of TMDBMovie
	 */
	public static ArrayList<TMDBMovie> retrieveDiscoverList(int page, double voteAvg, String genres) {
		ArrayList<TMDBMovie> movieList = new ArrayList<TMDBMovie>();

		Date now = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd");

		String url = Constant.TMDB_HOSTNAME + "discover/movie?api_key=" + Constant.TMDB_API_KEY
				+ "&primary_release_date.lte=" + sdf.format(now) + "&page=" + page + "&vote_average.gte=" + voteAvg
				+ "&vote_count.gte=" + 2 + "&with_genres=" + genres;

		try {
			InternetHelper internetHelper = new InternetHelper(url);
			String json = internetHelper.getHtmlData();

			if (json != null) {
				ObjectMapper mapper = new ObjectMapper();
				JsonNode parentNode = mapper.readTree(json);

				JsonNode resultNodes = parentNode.path("results");

				for (int i = 0; i < resultNodes.size(); i++) {
					JsonNode movieNode = resultNodes.get(i);
					TMDBMovie movie = new TMDBMovie(movieNode.get("id").asText());

					if (movieNode.get("original_language").asText().equals("en")
							|| movieNode.get("original_language").asText().equals("cn")) {

						if (movieNode.get("poster_path") == null
								|| movieNode.get("poster_path").toString().length() == 0
								|| movieNode.get("poster_path").toString().equals("null")) {
							movie.setImageUrl("/assets/img/no-poster.png");
						} else {
							movie.setImageUrl(TMDBHelper.getAbsImageUrl(movieNode.get("poster_path").asText(),
									Constant.TMDB_IMAGE_POSTER_SIZE));
						}

						if (movieNode.get("backdrop_path") == null
								|| movieNode.get("backdrop_path").toString().length() == 0
								|| movieNode.get("backdrop_path").toString().equals("null")) {
							movie.setImageBackUrl("/assets/img/no-poster.png");
						} else {
							movie.setImageBackUrl(TMDBHelper.getAbsImageUrl(movieNode.get("backdrop_path").asText(),
									Constant.TMDB_IMAGE_POSTER_SIZE));
						}

						movie.setTitle(movieNode.get("original_title").asText());
						movie.setOverview(StringEscapeUtils.unescapeHtml4(movieNode.get("overview").asText()));
						movie.setReleaseDate(movieNode.get("release_date").asText());
						movie.setRating(movieNode.get("vote_average").asDouble());

						// get the genres
						ArrayList<Integer> genreIdList = new ArrayList<Integer>();
						for (int g = 0; g < resultNodes.get(i).get("genre_ids").size(); g++) {
							int genreId = resultNodes.get(i).get("genre_ids").get(g).asInt();
							movie.getGenreList().add(new Genre(genreId, GenreEnum.getById(genreId).getName()));
							genreIdList.add(genreId);
						}
						movie.setGenreBit(genresBitBuilder(genreIdList));

						movieList.add(movie);
					}
				}
			}
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return movieList;
	}

	public static String translateGenreCategory(int genresCategory) {
		switch (genresCategory) {
		case 0:
			return "28%7C12";
		case 1:
			return "16";
		case 2:
			return "35";
		case 3:
			return "80%7C9648";
		case 4:
			return "27%7C53";
		case 5:
			return "10749";
		case 6:
			return "878";
		default:
			return "28%7C12%7C16%7C35%7C80%7C9648%7C27%7C53%7C10749%7C878";
		}
	}

	private static String genresBitBuilder(ArrayList<Integer> genresIdList) {
		StringBuilder genreBuilder = new StringBuilder("0000000");

		for (int i = 0; i < genresIdList.size(); i++) {
			int genresId = genresIdList.get(i);

			if (genresId == 28 || genresId == 12) {
				genreBuilder.setCharAt(0, '1');
			} else if (genresId == 16) {
				genreBuilder.setCharAt(1, '1');
			} else if (genresId == 35) {
				genreBuilder.setCharAt(2, '1');
			} else if (genresId == 80 || genresId == 9648) {
				genreBuilder.setCharAt(3, '1');
			} else if (genresId == 27 || genresId == 53) {
				genreBuilder.setCharAt(4, '1');
			} else if (genresId == 10749) {
				genreBuilder.setCharAt(5, '1');
			} else if (genresId == 878) {
				genreBuilder.setCharAt(6, '1');
			}
		}

		return genreBuilder.toString();
	}
}