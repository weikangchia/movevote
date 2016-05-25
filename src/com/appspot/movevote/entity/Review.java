package com.appspot.movevote.entity;

public class Review {
	private Person author;
	private String content;

	public Review(String name, String content) {
		author = new Person(name);
		this.content = content;
	}

	public Person getAuthor() {
		return author;
	}

	public void setAuthor(Person author) {
		this.author = author;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
}