package com.social.media.aggregator.entity;

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
@Table(name = "user_influencer")
@Schema(description = "Represents the relationship between a user and an influencer they follow")
public class UserInfluencer {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Schema(description = "Unique identifier for the user-influencer relationship", example = "1", required = true)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "user_id")
	@Schema(description = "The user who is following the influencer")
	private User user;

	@ManyToOne
	@JoinColumn(name = "influencer_id")
	@Schema(description = "The influencer being followed by the user")
	private Influencer influencer;
}
