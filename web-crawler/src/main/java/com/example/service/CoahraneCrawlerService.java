/**
 * 
 */
package com.example.service;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

/**
 * 
 */
@Service
public class CoahraneCrawlerService {

	final String baseurl = "https://www.cochranelibrary.com/cdsr/reviews/topics";
	final String outputfile = "C:/Users/shiva/eclipse-workspace/Web-Crawler/cochrane_reviews.txt";

	public void ReadReviewsurl(String baseurl) {
		// List the review urls from the coachrane library
		List<String> reviewurls = getreviewUrls();
		// Hold the reviews objects
		List<Review> reviews = new ArrayList<>();

		for (String reviewurl : reviewurls) {
			reviews.add(getreviewDetails(reviewurl));
		}
		writeReviewsToFile(reviews);

	}

	public void writeReviewsToFile(List<Review> reviews) {
		// TODO Auto-generated method stub
		BufferedWriter writer = null;
		try {
			writer = new BufferedWriter(new FileWriter(outputfile));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// Iterate through the reviews and write each one to the file
		for (Review review : reviews) {
			try {
				writer.write(review.toString());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				writer.newLine();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} // Write a new line after each review
		}

	}

	private List getreviewUrls() {
		List<String> reviewUrls = new ArrayList<>();
		// Fetch and parse the HTML document from the base URL
		Document doc = null;
		try {
			doc = Jsoup.connect(baseurl).get();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// Select all anchor elements with an href attribute
		Elements links = doc.select("a[href]");

		// Iterate through the links and collect the review URLs
		for (Element link : links) {
			String url = link.attr("href");
			// Check if the URL contains the specific path for reviews
			if (url.contains("/cdsr/doi/10.1002/")) {
				// Add the complete URL to the list
				reviewUrls.add("https://www.cochranelibrary.com" + url);
			}
		}
		return reviewUrls;
	}

	private Review getreviewDetails(String reviewurl) {
		// Fetch and parse the HTML document from the review URL
		Document doc = null;
		try {
			doc = Jsoup.connect(reviewurl).get();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// Extract the topic from the breadcrumb element
		String topic = doc.select(".breadcrumb__item--last").text();
		// Extract the title of the review
		String title = doc.select(".citation__title").text();
		// Select all author elements
		Elements authorElements = doc.select(".author-name");
		// List to hold the author names
		List<String> authors = new ArrayList<>();

		// Iterate through the author elements and collect the names
		for (Element author : authorElements) {
			authors.add(author.text());
		}

		// Join the author names into a single string
		String authorNames = String.join(", ", authors);
		// Extract the publication date of the review
		String date = doc.select(".citation__date").text();

		// Return a new Review object with the collected details
		return new Review(reviewurl, topic, title, authorNames, date);
	}
	
	
	
}
