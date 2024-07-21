package com.example.service;

public class Review {
	private String url; // URL of the review
    private String topic; // Topic of the review
    private String title; // Title of the review
    private String authors; // Authors of the review
    private String date; // Publication date of the review

    public Review(String reviewurl, String topic2, String title2, String authorNames, String date2) {
		// TODO Auto-generated constructor stub
	}

	// Override the toString method to return a pipe-delimited string representation of the review
    @Override
    public String toString() {
        return String.join("|", url, topic, title, authors, date);
    }
}
