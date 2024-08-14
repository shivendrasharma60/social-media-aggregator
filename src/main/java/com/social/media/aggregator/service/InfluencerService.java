package com.social.media.aggregator.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.social.media.aggregator.constants.Constants;
import com.social.media.aggregator.dto.InfluencerCreationDTO;
import com.social.media.aggregator.dto.PlatformRequestForInfluencer;
import com.social.media.aggregator.entity.Influencer;
import com.social.media.aggregator.entity.SocialMediaProfile;
import com.social.media.aggregator.exception.BusinessException;
import com.social.media.aggregator.repository.InfluencerRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class InfluencerService {

	@Autowired
	private InfluencerRepository influencerRepository;

	public Influencer addInfluencer(InfluencerCreationDTO influencerDTO) {
		try {
			// Persist the influencer and their profiles
			Influencer influencer = new Influencer();
			influencer.setName(influencerDTO.getName());
			influencer.setUniquehash(generate().toString());
			List<SocialMediaProfile> socialMediaProfiles = new ArrayList<SocialMediaProfile>();
			for (PlatformRequestForInfluencer profile : influencerDTO.getProfiles()) {
				SocialMediaProfile socialMediaProfile = new SocialMediaProfile();
				socialMediaProfile.setPlatform(profile.getPlatform());
				socialMediaProfile.setProfileUrl(profile.getProfileUrl());
				socialMediaProfile.setInfluencer(influencer);
				socialMediaProfiles.add(socialMediaProfile);
			}
			influencer.setProfiles(socialMediaProfiles);
			// Persist the influencer and their profiles
			return influencerRepository.save(influencer);
		} catch (Exception e) {
			log.error("Exception occurred in addInfluencer at InfluencerService : {}", ExceptionUtils.getStackTrace(e));
			throw new BusinessException(Constants.DEFAULT_ERROR_MESSAGE);
		}
	}

	public List<Influencer> getAllInfluencers() {
		return influencerRepository.findAll();
	}

	public static UUID generate() {
		UUID idOne = UUID.randomUUID();
		return idOne;
	}

}
