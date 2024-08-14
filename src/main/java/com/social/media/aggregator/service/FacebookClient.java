package com.social.media.aggregator.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class FacebookClient {

	private final String accessToken = "YOUR_FACEBOOK_ACCESS_TOKEN";

	public String getLatestFacebookPost(String pageId) {
		String url = String.format("https://graph.facebook.com/%s/posts?access_token=%s", pageId, accessToken);
		RestTemplate restTemplate = new RestTemplate();
		String response = restTemplate.getForObject(url, String.class);
		// Parse the response to get the latest post (this is simplified)
		return response;
	}
}
