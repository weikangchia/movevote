package com.appspot.movevote.entity;

public class InSingMovieShowPlace implements Comparable<InSingMovieShowPlace> {
	private String cinemaName;
	private String address;

	public InSingMovieShowPlace(String cinemaName, String address) {
		this.cinemaName = cinemaName;
		this.address = address;
	}

	public String getCinemaName() {
		return cinemaName;
	}

	public void setCinemaName(String cinemaName) {
		this.cinemaName = cinemaName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public boolean equals(Object o) {
		if ((o instanceof InSingMovieShowPlace)
				&& (((InSingMovieShowPlace) o).cinemaName.equals(cinemaName))) {
			return true;
		} else {
			return false;
		}
	}

	public String toString() {
		return cinemaName;
	}

	@Override
	public int compareTo(InSingMovieShowPlace o) {
		// TODO Auto-generated method stub
		return cinemaName.compareTo(o.cinemaName);
	}
}