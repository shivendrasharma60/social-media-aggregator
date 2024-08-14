package com.social.media.aggregator.entity;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Entity
@Table(name = "social_media_profiles")
@Data
@Schema(description = "Represents a social media profile associated with an influencer, containing multiple feeds")
public class SocialMediaProfile {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Schema(description = "Unique identifier for the social media profile", example = "1", required = true)
	private Long id;

	@Schema(description = "The social media platform (e.g., Twitter, Instagram, Facebook)", example = "Twitter", required = true)
	private String platform;

	@Schema(description = "The URL of the social media profile", example = "https://twitter.com/example", required = true)
	private String profileUrl;

	@ManyToOne
	@JoinColumn(name = "influencer_id")
	@Schema(description = "The influencer associated with this social media profile")
	private Influencer influencer;

	@OneToMany(mappedBy = "socialMediaProfile", cascade = CascadeType.ALL)
	@Schema(description = "List of social media feeds associated with this profile")
	private List<SocialMediaFeed> feeds;
}
