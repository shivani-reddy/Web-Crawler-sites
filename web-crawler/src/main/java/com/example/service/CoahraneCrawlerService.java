/**
 * 
 */
package com.example.service;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger; 
import org.slf4j.LoggerFactory; 
import org.apache.commons.lang3.StringEscapeUtils;

/**
 * 
 */
@Service
@Slf4j
public class CoahraneCrawlerService {

	final String baseurl = "https://www.cochranelibrary.com/home/topic-and-review-group-list.html";
	final String outputfile = "C:/Users/shiva/eclipse-workspace/Web-Crawler/cochrane_reviews.txt";
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
    
	public List<Review> searchReviewByTopic(String topic) {
		List<Review> reviews = new ArrayList<>();
		try {
			Document doc=Jsoup.connect(baseurl).get();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String topicUrl=getTopicUrl(topic);
		//perform search for reviews
		
		if(!topicUrl.isEmpty()) {
			Document topicdoc=null;
			try {
				topicdoc = Jsoup.connect(topicUrl).get();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Elements reviewElements=topicdoc.select(".search-results-item");
			for(Element reviewElement:reviewElements) {
				 String reviewUrl = "https://www.cochranelibrary.com" + reviewElement.select("a[href]");
                 String reviewTitle = reviewElement.select("a").text();
                 String reviewAuthors = reviewElement.select(".search-results-authors").text();
                 String reviewDate=reviewElement.select(".search-results-date").text();
                 reviews.add(new Review(reviewUrl, topic, reviewTitle, reviewAuthors, reviewDate));
   
			}
		}
		return reviews;
	}

	private String getTopicUrl(String topic) {
//		String encodedTopic = null;
//		try {
//			encodedTopic = java.net.URLEncoder.encode(topic, "UTF-8");
//		} catch (UnsupportedEncodingException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
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
        	 String unescapedTopicText = StringEscapeUtils.unescapeHtml4(topicText);
             if (unescapedTopicText.equalsIgnoreCase(topic)) {
                 return topicElement.attr("href");
             }
         }
         return "";
//         return "https://www.cochranelibrary.com/search?p_p_id=scolarissearchresultsportlet_WAR_scolarissearchresults" +
//                 "&p_p_lifecycle=0&p_p_state=normal&p_p_mode=view&p_p_col_id=column-1&p_p_col_count=1" +
//                 "&_scolarissearchresultsportlet_WAR_scolarissearchresults_displayText=" + encodedTopic +
//                 "&_scolarissearchresultsportlet_WAR_scolarissearchresults_searchText=" + encodedTopic +
//                 "&_scolarissearchresultsportlet_WAR_scolarissearchresults_searchType=basic" +
//                 "&_scolarissearchresultsportlet_WAR_scolarissearchresults_facetQueryField=topic_id" +
//                 "&_scolarissearchresultsportlet_WAR_scolarissearchresults_searchBy=13" +
//                 "&_scolarissearchresultsportlet_WAR_scolarissearchresults_orderBy=displayDate-true" +
//                 "&_scolarissearchresultsportlet_WAR_scolarissearchresults_facetDisplayName=" + encodedTopic +
//                 "&_scolarissearchresultsportlet_WAR_scolarissearchresults_facetQueryTerm=z1209270525153683793351219115280" +
//                 "&_scolarissearchresultsportlet_WAR_scolarissearchresults_facetCategory=Topics";
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
