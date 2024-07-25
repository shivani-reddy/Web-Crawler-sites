package com.example.service;

import lombok.AllArgsConstructor;
import lombok.Data;
/**
 * Represents a review entity with its relevant details.
 */
@Data
@AllArgsConstructor
public class Review {
	private String url; // URL of the review
    private String topic; // Topic of the review
    private String title; // Title of the review
    private String authors; // Authors of the review
    private String date; // Publication date of the review
    
    //Geters and setters not required for the functionality so didnt add them
	public Review(String reviewUrl, String topic2, String reviewTitle, String reviewAuthors, String reviewDate) {
		// TODO Auto-generated constructor stub
		this.url=reviewUrl;
		this.topic=topic2;
		this.title=reviewTitle;
		this.authors=reviewAuthors;
		this.date=reviewDate;
		
	}

	// Override the toString method to return a pipe-delimited string representation of the review
    @Override
    public String toString() {
    	return url + "|" + topic + "|" + title + "|" + authors + "|" + date;
    }
}
