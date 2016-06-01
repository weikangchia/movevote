package com.appspot.movevote.entity;

public class Constant {
	public static final String INSING_HOSTNAME = "https://www.insing.com/";
	public static final String DEV_HOSTNAME = "http://localhost:8888/";
	public static final String PUB_HOSTNAME = "https://movevote.appspot.com/";
	public static final String TMDB_API_KEY = "71fdea3ca66d6ddd2938d5c931e7ea82";
	public static final String TMDB_HOSTNAME = "https://api.themoviedb.org/3/";
	public static final String TMDB_IMAGE_URL = "https://image.tmdb.org/t/p/";

	// tmdb poster size
	public static final String TMDB_IMAGE_POSTER_SIZE = "w500";
	public static final String TMDB_IMAGE_LOGO_SIZE = "w92";
	public static final String TMDB_IMAGE_PROFILE_SIZE = "w45";

	// movie provider
	public static final String PROVIDER_INSING = "is";
	public static final String PROVIDER_TMDB = "tmdb";

	// Google Identity Toolkit
	public static final String GIT_COOKIE_NAME = "gtoken";
	public static final String GIT_SERVICE_ACCOUNT_EMAIL = "movevote@appspot.gserviceaccount.com";
	public static final String GIT_CLIENT_ID = "123477425429910-r99e3tmjtvst1qsfp0ttk9gcs9ffbq68.apps.googleusercontent.com";
	public static final String GIT_PROJECT_ID = "movevote";
	public static final String GIT_DEV_WIDGET_URL = DEV_HOSTNAME + "gitkit";
	public static final String GIT_PUB_WIDGET_URL = PUB_HOSTNAME + "gitkit";

	public static final String LOGIN_PATH = "/gitkit?mode=select";
}