package com.social.media.aggregator.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import com.social.media.aggregator.dto.SocialMediaFeedDTO;
import com.social.media.aggregator.entity.Influencer;
import com.social.media.aggregator.entity.SocialMediaFeed;
import com.social.media.aggregator.entity.SocialMediaProfile;
import com.social.media.aggregator.entity.User;
import com.social.media.aggregator.repository.InfluencerRepository;
import com.social.media.aggregator.repository.SocialMediaFeedRepository;
import com.social.media.aggregator.repository.SocialMediaProfileRepository;
import com.social.media.aggregator.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
public class FeedAggregationServiceTest {

	@InjectMocks
	private FeedAggregationService feedAggregationService;

	@Mock
	private UserRepository userRepository;

	@Mock
	private InfluencerRepository influencerRepository;

	@Mock
	private SocialMediaFeedRepository feedRepository;

	@Mock
	private SocialMediaProfileRepository socialMediaProfileRepository; // Mock this repository

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	public void testAggregateFeeds() {
		String username = "user1";
		List<String> platforms = Arrays.asList("Twitter", "Facebook");

		User user = new User();
		user.setId(1L);
		user.setUsername(username);

		Influencer influencer1 = new Influencer();
		influencer1.setId(1L);
		influencer1.setName("Influencer 1");

		SocialMediaProfile profile1 = new SocialMediaProfile();
		profile1.setId(1L);
		profile1.setPlatform("Twitter");
		profile1.setInfluencer(influencer1);

		SocialMediaProfile profile2 = new SocialMediaProfile();
		profile2.setId(2L);
		profile2.setPlatform("Facebook");
		profile2.setInfluencer(influencer1);

		influencer1.setProfiles(Arrays.asList(profile1, profile2));

		SocialMediaFeed feed1 = new SocialMediaFeed();
		feed1.setId(1L);
		feed1.setContent("Tweet 1");
		feed1.setTimestamp(LocalDateTime.now());
		feed1.setSocialMediaProfile(profile1);

		SocialMediaFeed feed2 = new SocialMediaFeed();
		feed2.setId(2L);
		feed2.setContent("Post 1");
		feed2.setTimestamp(LocalDateTime.now());
		feed2.setSocialMediaProfile(profile2);

		when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));
		when(influencerRepository.findByFollowers_Id(user.getId())).thenReturn(Arrays.asList(influencer1));
		when(feedRepository.findBySocialMediaProfile(profile1)).thenReturn(Arrays.asList(feed1));
		when(feedRepository.findBySocialMediaProfile(profile2)).thenReturn(Arrays.asList(feed2));

		List<SocialMediaFeedDTO> result = feedAggregationService.aggregateFeeds(username, platforms);

		assertEquals(2, result.size());
		assertEquals("Tweet 1", result.get(0).getContent());
		assertEquals("Post 1", result.get(1).getContent());
	}

	@Test
	public void testAggregateFeeds_UserNotFound() {
		String username = "userNotFound";
		List<String> platforms = Arrays.asList("Twitter", "Facebook");

		when(userRepository.findByUsername(username)).thenReturn(Optional.empty());

		Exception exception = null;
		try {
			feedAggregationService.aggregateFeeds(username, platforms);
		} catch (Exception e) {
			exception = e;
		}

		assertEquals("User not found", exception.getMessage());
	}

	@Test
	public void testGetAllFeeds() {
		SocialMediaFeed feed1 = new SocialMediaFeed();
		feed1.setId(1L);
		feed1.setContent("Feed 1");
		feed1.setTimestamp(LocalDateTime.now());

		SocialMediaFeed feed2 = new SocialMediaFeed();
		feed2.setId(2L);
		feed2.setContent("Feed 2");
		feed2.setTimestamp(LocalDateTime.now());

		when(feedRepository.findAll()).thenReturn(Arrays.asList(feed1, feed2));

		List<SocialMediaFeedDTO> result = feedAggregationService.getAllFeeds();

		assertEquals(2, result.size());
		assertEquals("Feed 1", result.get(0).getContent());
		assertEquals("Feed 2", result.get(1).getContent());
	}

	@Test
	public void testGetLatestFeeds() {
		String platform = "Twitter";

		SocialMediaProfile profile = new SocialMediaProfile();
		profile.setPlatform(platform);

		List<SocialMediaProfile> profiles = Arrays.asList(profile);

		SocialMediaFeed latestFeed = new SocialMediaFeed();
		latestFeed.setId(1L);
		latestFeed.setContent("Latest Tweet");
		latestFeed.setTimestamp(LocalDateTime.now());

		// Assuming the repository returns a list of profiles
		when(socialMediaProfileRepository.findByPlatform(platform)).thenReturn(profiles);
		when(feedRepository.findTopBySocialMediaProfileOrderByTimestampDesc(profile))
				.thenReturn(Optional.of(latestFeed));

		List<SocialMediaFeedDTO> result = feedAggregationService.getLatestFeeds(Arrays.asList(platform));

		assertEquals(1, result.size());
		assertEquals("Latest Tweet", result.get(0).getContent());
	}

	@Test
	public void testGetLatestFeeds_NoFeeds() {
		String platform = "Twitter";

		SocialMediaProfile profile = new SocialMediaProfile();
		profile.setPlatform(platform);

		List<SocialMediaProfile> profiles = Arrays.asList(profile);

		// Assuming the repository returns a list of profiles
		when(socialMediaProfileRepository.findByPlatform(platform)).thenReturn(profiles);
		when(feedRepository.findTopBySocialMediaProfileOrderByTimestampDesc(profile)).thenReturn(Optional.empty());

		List<SocialMediaFeedDTO> result = feedAggregationService.getLatestFeeds(Arrays.asList(platform));

		assertEquals(0, result.size());
	}

}
