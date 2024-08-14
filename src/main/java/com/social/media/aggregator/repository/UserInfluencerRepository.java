package com.social.media.aggregator.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.social.media.aggregator.entity.Influencer;
import com.social.media.aggregator.entity.User;
import com.social.media.aggregator.entity.UserInfluencer;

public interface UserInfluencerRepository extends JpaRepository<UserInfluencer, Long> {

	// Find all UserInfluencer relationships for a given user
	List<UserInfluencer> findByUser(User user);

	// Find all UserInfluencer relationships for a given influencer
	List<UserInfluencer> findByInfluencer(Influencer influencer);

	// Find all influencers followed by a user using the user's ID
	List<UserInfluencer> findByUserId(Long userId);

	// Find all users following a particular influencer using the influencer's ID
	List<UserInfluencer> findByInfluencerId(Long influencerId);
}
