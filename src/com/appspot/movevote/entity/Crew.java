package com.appspot.movevote.entity;

import com.appspot.movevote.helper.TMDBHelper;

public class Crew extends Person {
	private String department;
	private String job;

	public Crew(String id, String name, String profilePath, String department,
			String job) {
		super(id, name, profilePath);
		this.department = department;
		this.job = job;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getJob() {
		return job;
	}

	public void setJob(String job) {
		this.job = job;
	}

	@Override
	public String getProfilePath() {
		// TODO Auto-generated method stub
		return TMDBHelper.getAbsImageUrl(super.getProfilePath(),
				Constant.TMDB_IMAGE_PROFILE_SIZE);
	}
}