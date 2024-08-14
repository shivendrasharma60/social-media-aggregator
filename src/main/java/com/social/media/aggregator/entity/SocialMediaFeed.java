package com.social.media.aggregator.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Entity
@Data
@Table(name = "social_media_feed")
@Schema(description = "Represents a social media feed post, including content, media, and engagement metrics")
public class SocialMediaFeed {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Schema(description = "Unique identifier for the social media feed post", example = "1", required = true)
	private Long id;

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

	@ManyToOne
	@JoinColumn(name = "social_media_profile_id")
	@Schema(description = "The social media profile associated with this post")
	private SocialMediaProfile socialMediaProfile;
}
