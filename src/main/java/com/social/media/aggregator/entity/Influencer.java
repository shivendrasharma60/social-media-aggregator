package com.social.media.aggregator.entity;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Entity
@Table(name = "influencers") // Naming the table "influencers"
@Data
@Schema(description = "Represents an influencer with multiple social media profiles and followers")
public class Influencer {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Schema(description = "Unique identifier for the influencer", example = "1", required = true)
	private Long id;

	@Schema(description = "Name of the influencer", example = "John Doe", required = true)
	private String name;

	@Schema(description = "Unique hash for the influencer", example = "abc123hash", required = true)
	private String uniquehash;

	@OneToMany(mappedBy = "influencer", cascade = CascadeType.ALL)
	@Schema(description = "List of social media profiles associated with the influencer")
	private List<SocialMediaProfile> profiles;

	@ManyToMany(mappedBy = "influencers")
	@Schema(description = "List of users who follow the influencer")
	private List<User> followers;

}
