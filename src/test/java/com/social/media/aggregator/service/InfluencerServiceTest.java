package com.social.media.aggregator.service;

import com.social.media.aggregator.dto.InfluencerCreationDTO;
import com.social.media.aggregator.dto.PlatformRequestForInfluencer;
import com.social.media.aggregator.entity.Influencer;
import com.social.media.aggregator.entity.SocialMediaProfile;
import com.social.media.aggregator.exception.BusinessException;
import com.social.media.aggregator.repository.InfluencerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class InfluencerServiceTest {

	@InjectMocks
	private InfluencerService influencerService;

	@Mock
	private InfluencerRepository influencerRepository;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	public void testAddInfluencer() {
		// Prepare the input DTO
		InfluencerCreationDTO influencerDTO = new InfluencerCreationDTO();
		influencerDTO.setName("Test Influencer");

		PlatformRequestForInfluencer profile1 = new PlatformRequestForInfluencer();
		profile1.setPlatform("Twitter");
		profile1.setProfileUrl("https://twitter.com/testinfluencer");

		PlatformRequestForInfluencer profile2 = new PlatformRequestForInfluencer();
		profile2.setPlatform("Facebook");
		profile2.setProfileUrl("https://facebook.com/testinfluencer");

		influencerDTO.setProfiles(Arrays.asList(profile1, profile2));

		// Prepare the expected Influencer entity
		Influencer influencer = new Influencer();
		influencer.setName("Test Influencer");
		influencer.setUniquehash(UUID.randomUUID().toString());

		List<SocialMediaProfile> socialMediaProfiles = new ArrayList<>();
		SocialMediaProfile socialMediaProfile1 = new SocialMediaProfile();
		socialMediaProfile1.setPlatform("Twitter");
		socialMediaProfile1.setProfileUrl("https://twitter.com/testinfluencer");
		socialMediaProfile1.setInfluencer(influencer);
		socialMediaProfiles.add(socialMediaProfile1);

		SocialMediaProfile socialMediaProfile2 = new SocialMediaProfile();
		socialMediaProfile2.setPlatform("Facebook");
		socialMediaProfile2.setProfileUrl("https://facebook.com/testinfluencer");
		socialMediaProfile2.setInfluencer(influencer);
		socialMediaProfiles.add(socialMediaProfile2);

		influencer.setProfiles(socialMediaProfiles);

		when(influencerRepository.save(any(Influencer.class))).thenReturn(influencer);

		// Call the method to test
		Influencer result = influencerService.addInfluencer(influencerDTO);

		// Verify the result
		assertNotNull(result);
		assertEquals("Test Influencer", result.getName());
		assertEquals(2, result.getProfiles().size());
		verify(influencerRepository, times(1)).save(any(Influencer.class));
	}

	@Test
	public void testAddInfluencer_Exception() {
		InfluencerCreationDTO influencerDTO = new InfluencerCreationDTO();
		influencerDTO.setName("Test Influencer");

		lenient().when(influencerRepository.save(any(Influencer.class)))
				.thenThrow(new BusinessException("Test Exception"));

		Exception exception = assertThrows(BusinessException.class, () -> {
			influencerService.addInfluencer(influencerDTO);
		});

		assertEquals("Something went wrong", exception.getMessage());
	}

	@Test
	public void testGetAllInfluencers() {
		Influencer influencer1 = new Influencer();
		influencer1.setName("Influencer 1");

		Influencer influencer2 = new Influencer();
		influencer2.setName("Influencer 2");

		when(influencerRepository.findAll()).thenReturn(Arrays.asList(influencer1, influencer2));

		List<Influencer> result = influencerService.getAllInfluencers();

		assertEquals(2, result.size());
		assertEquals("Influencer 1", result.get(0).getName());
		assertEquals("Influencer 2", result.get(1).getName());
		verify(influencerRepository, times(1)).findAll();
	}
}
