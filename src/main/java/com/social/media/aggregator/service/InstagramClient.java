package com.social.media.aggregator.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class InstagramClient {

	// This also can be made dynamic as this will be unique for each an every user
	private final String accessToken = "YOUR_INSTAGRAM_ACCESS_TOKEN";

	public String getLatestInstagramPost(String userId) {
		String url = String.format(
				"https://graph.instagram.com/%s/media?fields=id,caption,media_url,timestamp&access_token=%s", userId,
				accessToken);
		RestTemplate restTemplate = new RestTemplate();
		String response = restTemplate.getForObject(url, String.class);
		// Parse the response to get the latest post (this is simplified)
		return response;
	}
}
