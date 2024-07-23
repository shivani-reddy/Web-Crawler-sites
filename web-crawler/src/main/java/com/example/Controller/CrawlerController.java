/**
 * 
 */
package com.example.Controller;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.service.CoahraneCrawlerService;
import com.example.service.Review;

/**
 * 
 */
@RestController
public class CrawlerController {
	    @Autowired
	    private CoahraneCrawlerService cochraneCrawlerService;

//	    @GetMapping("/crawl")
//	    public String crawlReviews() {
//	        cochraneCrawlerService.ReadReviewsurl();
//	        return "Crawling started. Check the log for updates.";
//	    }
	    @GetMapping("/topics")
	    public List<String> getTopics() {
	        return cochraneCrawlerService.getTopics();
	        
	    }
	    

	    // Endpoint to search reviews by topic
	    @GetMapping("/search")
	    public String searchReviews(@RequestParam String topic) {
//	    	String encodedTopic=null;
//			try {
//				encodedTopic = URLEncoder.encode(topic, "UTF-8");
//			} catch (UnsupportedEncodingException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
	        cochraneCrawlerService.readReviewByTopic(topic);
	        return topic;
	    }
	}
