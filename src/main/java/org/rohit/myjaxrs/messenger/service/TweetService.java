package org.rohit.myjaxrs.messenger.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import org.rohit.javabrains.models.Tweet;
import org.rohit.myjaxrs.messenger.database.MyDatabase;

public class TweetService {
	
	private Map<Long,Tweet> tweets = MyDatabase.getInstance().getTweets();
	private MyDatabase databaseService = MyDatabase.getInstance();
	
	public List<Tweet> getTweets() {
		databaseService.getTweets();
		return new ArrayList<Tweet>(tweets.values());
	}

	public Tweet getTweet(long tweetId) {
		
		return tweets.get(tweetId);
	}

	public Tweet addTweet(Tweet tweet) {
		long id = getNextId();
		tweet.setTweetId(id);
		tweet.setCreatedAt(Calendar.getInstance().getTime().toString());
		databaseService.saveTweet(tweet);
		tweets.put(id, tweet);
		return tweet;
	}
	
	private long getNextId(){
		long id = -1;
		for(Long key:tweets.keySet()){
			id = key;
		}
		return id+1;
	}

}
