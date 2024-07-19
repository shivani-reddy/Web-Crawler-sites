/**
 * 
 */
package com.example.service;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 */
@service
public class CoahraneCrawlerService {
	
	final String baseurl="https://www.cochranelibrary.com/cdsr/reviews/topics";
	final String outputfile="C:/Users/shiva/eclipse-workspace/Web-Crawler/cochrane_reviews.txt";
    
	public reviews ReadReviewsurl(String baseurl) {
		//List the review urls from the coachrane library
		List reviewurls=getreviewUrls();
		//Hold the reviews objects
		List reviews=new ArrayList<>();
		
		for(String reviewurl:reviewurls) {
			reviews.add(getreviewDetails(reviewurl));
		}
		writeReviewsToFile(reviews);
		
		
	}
	private void writeReviewsToFile(List reviews) {
		// TODO Auto-generated method stub
		
	}
	private List getreviewUrls() {
		Document doc=jsoup.conn(baseurl):
			
		return null;
	}
	private review getreviewDetails(String reviewurl) {
		
		return null;
	}
	}
	
	
	
	
	
    
}
