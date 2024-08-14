package com.social.media.aggregator.entity;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Entity
@Table(name = "users") // Renaming the table to "users" to avoid conflict
@Data
@Schema(description = "Represents a user in the system who can follow multiple influencers")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Schema(description = "Unique identifier for the user", example = "1", required = true)
	private Long id;

	@Schema(description = "Username of the user", example = "john_doe", required = true)
	private String username;

	@Schema(description = "Password for the user", example = "password123", required = true)
	private String password;

	@ManyToMany
	@JoinTable(name = "user_influencer", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "influencer_id"))
	@Schema(description = "List of influencers that the user follows")
	private List<Influencer> influencers;
}
