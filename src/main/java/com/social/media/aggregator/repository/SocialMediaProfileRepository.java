package com.social.media.aggregator.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.social.media.aggregator.entity.Influencer;
import com.social.media.aggregator.entity.SocialMediaProfile;

@Repository
public interface SocialMediaProfileRepository extends JpaRepository<SocialMediaProfile, Long> {
	List<SocialMediaProfile> findByPlatform(String platform);

	SocialMediaProfile findByPlatformAndProfileUrl(String platform, String profileUrl);

}
