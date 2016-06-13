package com.appspot.movevote.entity;

public enum GenreEnum {
	ACTION(28, "Action"),
	ADVENTURE(12, "Adventure"),
	ANIMATION(16, "Animation"),
	COMEDY(35, "Comedy"),
	CRIME(80, "Crime"),
	DOCUMENTARY(99, "Documentary"),
	DRAMA(18, "Drama"),
	FAMILY(10751, "Family"),
	FANTASY(14, "Fantasy"),
	FOREIGN(10769, "Foreign"),
	HISTORY(36, "History"),
	HORROR(27, "Horror"),
	MUSIC(10402, "Music"),
	MYSTERY(9648, "Mystery"),
	ROMANCE(10749, "Romance"),
	SCIENCE_FICTION(878, "Science Fiction"),
	TV_MOVIE(10770, "TV Movie"),
	THRILLER(53, "Thriller"),
	WAR(10752, "War"),
	WESTERN(37, "Western"),
	ACTION_ADVENTURE(10759, "Action & Adventure"),
	EDUCATION(10761, "Education"),
	KIDS(10762, "Kids"),
	NEWS(10763, "News"),
	REALITY(10764, "Reality"),
	SCI_FI_FANTASY(10765, "Sci-Fi & Fantasy"),
	SOAP(10766, "Soap"),
	TALK(10767, "Talk"),
	WAR_POLITICS(10768, "War & Politics");

	public static final int SIZE = 29;
	private String name;
	private int id;

	GenreEnum(int id, String name) {
		this.id = id;
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public static GenreEnum getById(int id) {
		for (GenreEnum ge : GenreEnum.values()) {
			if (ge.getId() == id) {
				return ge;
			}
		}

		return null;
	}
}