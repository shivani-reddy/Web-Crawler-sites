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
//Controller for handling the review serach requests
@RestController
public class CrawlerController {
	    @Autowired
	    private CoahraneCrawlerService cochraneCrawlerService;

        //End point to get all the topics
	    @GetMapping("/topics")
	    public List<String> getTopics() {
	        return cochraneCrawlerService.getTopics();
	        
	    }
	    /**
	     * Searches for reviews based on the provided topic.
	     *
	     * @param topic the topic to search reviews for
	     * @return a list of reviews matching the topic
	     */
	    //If there is a special character like & you have to give it as %26 which needs to be fixed in further sometimes the url isnt adding and it needs to be fixed.
	    @GetMapping("/search")
	    public String searchReviews(@RequestParam String topic) {
	        cochraneCrawlerService.readReviewByTopic(topic);
	        return topic;
	    }
//	    @GetMapping("/crawl")
//	    public String crawlReviews() {
//	        cochraneCrawlerService.ReadReviewsurl();
//	        return "Crawling started. Check the log for updates.";
//	    }
	}
