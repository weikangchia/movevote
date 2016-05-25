package com.appspot.movevote.helper;

import com.appspot.movevote.entity.Constant;

public class TMDBHelper {
	/**
	 * Helper method to form absolute image url
	 * 
	 * @pre type must be of Constant.TMDB_IMAGE_POSTER_SIZE,
	 *      Constant.TMDB_IMAGE_LOGO_SIZE or Constant.TMDB_IMAGE_PROFILE_SIZE
	 * @param path
	 *            absolute image path
	 * @param type
	 *            Constant.TMDB_IMAGE_POSTER_SIZE, Constant.TMDB_IMAGE_LOGO_SIZE
	 *            or Constant.TMDB_IMAGE_PROFILE_SIZE
	 * 
	 * @return url: absolute image url
	 */
	public static String getAbsImageUrl(String path, String type) {
		return Constant.TMDB_IMAGE_URL + type + path;
	}
}
