package com.social.media.aggregator.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.social.media.aggregator.constants.Constants;
import com.social.media.aggregator.dto.SocialMediaFeedDTO;
import com.social.media.aggregator.entity.Influencer;
import com.social.media.aggregator.entity.SocialMediaFeed;
import com.social.media.aggregator.entity.SocialMediaProfile;
import com.social.media.aggregator.entity.User;
import com.social.media.aggregator.exception.BusinessException;
import com.social.media.aggregator.repository.InfluencerRepository;
import com.social.media.aggregator.repository.SocialMediaFeedRepository;
import com.social.media.aggregator.repository.SocialMediaProfileRepository;
import com.social.media.aggregator.repository.UserRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class FeedAggregationService {

	@Autowired
	private SocialMediaFeedRepository feedRepository;

	@Autowired
	private SocialMediaProfileRepository socialMediaProfileRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private InfluencerRepository influencerRepository;

	// Aggregates feeds for a user based on the influencers they follow
	public List<SocialMediaFeedDTO> aggregateFeeds(String username, List<String> platforms) {
		try {
			List<SocialMediaFeedDTO> feedDTOs = new ArrayList<>();

			// Find the user by username
			Optional<User> userOpt = userRepository.findByUsername(username);
			if (!userOpt.isPresent()) {
				throw new BusinessException("User not found");
			}

			User user = userOpt.get();

			// Get the list of influencers the user follows
			List<Influencer> influencers = influencerRepository.findByFollowers_Id(user.getId());

			// For each influencer, fetch their social media feeds
			for (Influencer influencer : influencers) {
				for (SocialMediaProfile profile : influencer.getProfiles()) {
					if (platforms.contains(profile.getPlatform())) {
						// Fetch feeds from the in-memory database based on the profile
						List<SocialMediaFeed> profileFeeds = feedRepository.findBySocialMediaProfile(profile);

						if (!profileFeeds.isEmpty()) {
							for (SocialMediaFeed feed : profileFeeds) {
								SocialMediaFeedDTO dto = new SocialMediaFeedDTO(feed.getContent(), feed.getMediaUrl(),
										feed.getTimestamp(), feed.getLikes(), feed.getShares(), feed.getComments());
								feedDTOs.add(dto);
							}
						}
					}
				}
			}

			return feedDTOs;
		} catch (BusinessException e) {
			log.error("BusinessException occurred in aggregateFeeds at FeedAggregationService : {}",
					ExceptionUtils.getStackTrace(e));
			throw new BusinessException(e.getMessage());
		} catch (Exception e) {
			log.error("Exception occurred in aggregateFeeds at FeedAggregationService : {}",
					ExceptionUtils.getStackTrace(e));
			throw new BusinessException(Constants.DEFAULT_ERROR_MESSAGE);
		}
	}

	// This method returns the latest feeds based on platforms
	public List<SocialMediaFeedDTO> getLatestFeeds(List<String> platforms) {
		List<SocialMediaFeedDTO> latestFeeds = new ArrayList<>();
		for (String platform : platforms) {
			List<SocialMediaProfile> profiles = socialMediaProfileRepository.findByPlatform(platform);
			for (SocialMediaProfile profile : profiles) {
				Optional<SocialMediaFeed> latestFeed = feedRepository
						.findTopBySocialMediaProfileOrderByTimestampDesc(profile);
				latestFeed.ifPresent(feed -> {
					SocialMediaFeedDTO dto = new SocialMediaFeedDTO();
					dto.setContent(feed.getContent());
					dto.setMediaUrl(feed.getMediaUrl());
					dto.setTimestamp(feed.getTimestamp());
					dto.setLikes(feed.getLikes());
					dto.setShares(feed.getShares());
					dto.setComments(feed.getComments());
					latestFeeds.add(dto);
				});
			}
		}
		return latestFeeds;
	}

	public List<SocialMediaFeedDTO> getAllFeeds() {
		try {
			List<SocialMediaFeedDTO> feedDTOs = new ArrayList<>();
			List<SocialMediaFeed> feeds = feedRepository.findAll();

			for (SocialMediaFeed feed : feeds) {
				SocialMediaFeedDTO dto = new SocialMediaFeedDTO(feed.getContent(), feed.getMediaUrl(),
						feed.getTimestamp(), feed.getLikes(), feed.getShares(), feed.getComments());
				feedDTOs.add(dto);
			}

			return feedDTOs;
		} catch (BusinessException e) {
			log.error("BusinessException occurred in getAllFeeds at FeedAggregationService : {}",
					ExceptionUtils.getStackTrace(e));
			throw new BusinessException(e.getMessage());
		} catch (Exception e) {
			log.error("Exception occurred in getAllFeeds at FeedAggregationService : {}",
					ExceptionUtils.getStackTrace(e));
			throw new BusinessException(Constants.DEFAULT_ERROR_MESSAGE);
		}
	}
}
