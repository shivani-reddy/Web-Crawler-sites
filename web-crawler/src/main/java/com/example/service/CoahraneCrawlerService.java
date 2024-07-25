/**
 * 
 */
package com.example.service;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.WebDriver;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger; 
import org.slf4j.LoggerFactory; 
import org.apache.commons.lang3.StringEscapeUtils;


/**
 * Service class for handling review search operations.
 */
@Service
@Slf4j
public class CoahraneCrawlerService {

	String baseurl = "https://www.cochranelibrary.com/home/topic-and-review-group-list.html";
	String outputfile = "C:/Users/shiva/eclipse-workspace/Web-Crawler/cochrane_reviews.txt";
	//Update with your file name
	
	
	//fetch all the topics
	public List<String> getTopics() {
        List<String> topics = new ArrayList<>();
       
            // Fetch and parse the HTML document from the base URL
            Document doc=null;
			try {
				doc = Jsoup.connect(baseurl).get();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            // Select all topic elements
            Elements topicElements = doc.select(".browse-by-list-item a");

            // Iterate through the topic elements and collect the topic names
            for (Element topicElement : topicElements) {
                String topic = topicElement.text();
                topics.add(topic);
            }
        
        return topics;
    }
	
	
	//Read reviews by topic
	public void readReviewByTopic(String topic) {
		List<Review> reviews=searchReviewByTopic(topic);
		writeReviewsToFile(reviews);
	}
	
	//Written as of verifying the html coming from the pagination URL
	public void downloadTopicDoc(String url, String filePath) {
        try {
            Document doc = Jsoup.connect(url).get();
            try (FileWriter writer = new FileWriter(filePath)) {
                writer.write(doc.outerHtml());
            }
            System.out.println("HTML content downloaded to: " + filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
	
	/**
     * Searches for reviews based on the provided topic.
     *
     * @param topic the topic to search reviews for
     * @return a list of reviews matching the topic
     */
	public List<Review> searchReviewByTopic(String topic) {
		List<Review> reviews = new ArrayList<>();
		String topicUrl=getTopicUrl(topic);
		String currentPageUrl = topicUrl;
         while (currentPageUrl != null && !currentPageUrl.isEmpty()) {
        	// downloadTopicDoc(currentPageUrl, "C:/Users/shiva/eclipse-workspace/Web-Crawler/topicDoc1.html");
        	 Document topicDoc=null;
			try {
				topicDoc = Jsoup.connect(currentPageUrl).get();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (topicDoc == null) {
				System.out.println("topic Doc is empty");
	            break; // Exit the loop if the document couldn't be fetched
	        }
            Elements reviewElements = topicDoc.select(".search-results-item");
            if (reviewElements.isEmpty()) {
               System.out.println("No review elements found on the page.");
            }
            //System.out.println("review elements"+reviewElements);
            
            // Check if there are review elements on the page
            for(Element reviewElement:reviewElements) {
				 Element linkElement = reviewElement.selectFirst("a[href*=doi/10.1002]");
		            String reviewUrl ="https://www.cochranelibrary.com" + linkElement.attr("href");
		            
		            // Extract the review title
		            String reviewTitle = linkElement.text();
		        
		            // Extract the review authors
		            Element authorsElement = reviewElement.selectFirst(".search-result-authors");
		            String reviewAuthors = authorsElement.text();
		           
		            // Extract the review date
		            Element dateElement = reviewElement.selectFirst(".search-result-date");
		            String reviewDate = dateElement.text();
		            
                 reviews.add(new Review(reviewUrl, topic, reviewTitle, reviewAuthors, reviewDate));
                 
			}
            Element nextPageLink = topicDoc.selectFirst("div.pagination-next-link a");
            //System.out.println("nextpage link"+nextPageLink);
            if (nextPageLink != null && nextPageLink.hasAttr("href")) {
                currentPageUrl =nextPageLink.attr("href");
                //System.out.println("currentpage link"+currentPageUrl);
            } else {
            	//System.out.println("not found");
                currentPageUrl = null;
	    }
            }
        return reviews;
	}
	/**
     * Retrieves the topic URL for the given topic.
     *
     * @param topic the topic to get the URL for
     * @return the URL for the topic
     */

	private String getTopicUrl(String topic) {
		Document doc=null;
		try {
			doc = Jsoup.connect(baseurl).get();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Elements topicElements = doc.select(".browse-by-list-item a");
		
		for (Element topicElement : topicElements) {
        	 String topicText = topicElement.select(".btn-link.browse-by-list-item-link").text();
        	 //String unescapedTopicText = StringEscapeUtils.unescapeHtml4(topicText);
             if (topicText.equalsIgnoreCase(topic)) {
                 return topicElement.attr("href");
             }
         }
         return "";

	}
	/**
     * Writes the list of reviews to a file.
     *
     * @param reviews the list of reviews to write to the file
     */
	public void writeReviewsToFile(List<Review> reviews) {
		// TODO Auto-generated method stub
		FileWriter writer = null;
		try {
			writer = new FileWriter(outputfile);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// Iterate through the reviews and write each one to the file
		for (Review review : reviews) {
			try {
				writer.write(review.toString());
				writer.write(System.lineSeparator());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

//	public void ReadReviewsurl() {
//		// List the review urls from the coachrane library
//		List<String> reviewurls = getreviewUrls();
//		//System.out.print(reviewurls);
//		// Hold the reviews objects
//		List<Review> reviews = new ArrayList<>();
//		
//		
//		for (String reviewurl : reviewurls) {
//			reviews.add(getreviewDetails(reviewurl));
//		}
//		writeReviewsToFile(reviews);
//
//	}
//	private List getreviewUrls() {
//		List<String> reviewUrls = new ArrayList<>();
//		// Fetch and parse the HTML document from the base URL
//		Document doc = null;
//		try {
//			doc = Jsoup.connect(baseurl).get();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		// Select all anchor elements with an href attribute
//		Elements links = doc.select("a[href]");
//
//		// Iterate through the links and collect the review URLs
//		for (Element link : links) {
//			String url = link.attr("href");
//			// Check if the URL contains the specific path for reviews
//			if (url.contains("/cdsr/doi/10.1002/")) {
//				// Add the complete URL to the list
//				reviewUrls.add("https://www.cochranelibrary.com" + url);
//			}
//		}
//	    int n=reviewUrls.size();
//	    System.out.print(n);
//		return reviewUrls;
//	}
//
//	private Review getreviewDetails(String reviewurl) {
//		// Fetch and parse the HTML document from the review URL
//		Document doc = null;
//		try {
//			doc = Jsoup.connect(reviewurl).get();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		// Extract the topic from the breadcrumb element
//		String topic = doc.select(".breadcrumb__item--last").text();
//		// Extract the title of the review
//		String title = doc.select(".citation__title").text();
//		// Select all author elements
//		Elements authorElements = doc.select(".author-name");
//		// List to hold the author names
//		List<String> authors = new ArrayList<>();
//
//		// Iterate through the author elements and collect the names
//		for (Element author : authorElements) {
//			authors.add(author.text());
//		}
//
//		// Join the author names into a single string
//		String authorNames = String.join(", ", authors);
//		// Extract the publication date of the review
//		String date = doc.select(".citation__date").text();
//
//		// Return a new Review object with the collected details
//		return new Review(reviewurl, topic, title, authorNames, date);
//	}
//	
	
	
}
