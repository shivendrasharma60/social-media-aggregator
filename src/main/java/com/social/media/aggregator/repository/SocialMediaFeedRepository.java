package com.social.media.aggregator.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.social.media.aggregator.entity.SocialMediaFeed;
import com.social.media.aggregator.entity.SocialMediaProfile;

@Repository
public interface SocialMediaFeedRepository extends JpaRepository<SocialMediaFeed, Long> {

	// Method to find all feeds by social media profile
	List<SocialMediaFeed> findBySocialMediaProfile(SocialMediaProfile socialMediaProfile);

	// Method to find the latest feed by social media profile
	Optional<SocialMediaFeed> findTopBySocialMediaProfileOrderByTimestampDesc(SocialMediaProfile socialMediaProfile);
}
