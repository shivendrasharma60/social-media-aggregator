package com.social.media.aggregator.dto;

import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "DTO representing a social media feed post, including content, media, and engagement metrics")
public class SocialMediaFeedDTO {

	@Schema(description = "Content of the social media post", example = "Excited to announce our new product launch!", required = true)
	private String content;

	@Schema(description = "URL of any media associated with the post", example = "https://example.com/image.jpg", required = false)
	private String mediaUrl;

	@Schema(description = "Timestamp of when the post was created", example = "2024-08-13T10:00:00", required = true)
	private LocalDateTime timestamp;

	@Schema(description = "Number of likes the post received", example = "250", required = true)
	private int likes;

	@Schema(description = "Number of shares the post received", example = "100", required = true)
	private int shares;

	@Schema(description = "Number of comments on the post", example = "50", required = true)
	private int comments;

	public SocialMediaFeedDTO(String content, String mediaUrl, LocalDateTime timestamp, int likes, int shares,
			int comments) {
		this.content = content;
		this.mediaUrl = mediaUrl;
		this.timestamp = timestamp;
		this.likes = likes;
		this.shares = shares;
		this.comments = comments;
	}

	public SocialMediaFeedDTO() {
	}
}
