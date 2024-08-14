package com.social.media.aggregator.service;

import org.springframework.stereotype.Service;

import twitter4j.ResponseList;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;

@Service
public class TwitterClient {

	private Twitter twitter;

	public TwitterClient() {
		twitter = new TwitterFactory().getInstance();
		twitter.setOAuthConsumer("YOUR_CONSUMER_KEY", "YOUR_CONSUMER_SECRET");
		twitter.setOAuthAccessToken(new AccessToken("YOUR_ACCESS_TOKEN", "YOUR_ACCESS_TOKEN_SECRET"));
	}

	public String getLatestTweet(String username) {
		try {
			ResponseList<Status> statuses = twitter.getUserTimeline(username);
			if (!statuses.isEmpty()) {
				Status latestStatus = statuses.get(0);
				return latestStatus.getText();
			}
		} catch (TwitterException e) {
			e.printStackTrace();
		}
		return null;
	}
}
