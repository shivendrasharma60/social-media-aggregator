package com.social.media.aggregator.service;

import java.util.Base64;
import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.social.media.aggregator.constants.Constants;
import com.social.media.aggregator.dto.UserCreationDTO;
import com.social.media.aggregator.entity.Influencer;
import com.social.media.aggregator.entity.User;
import com.social.media.aggregator.entity.UserInfluencer;
import com.social.media.aggregator.exception.BusinessException;
import com.social.media.aggregator.repository.InfluencerRepository;
import com.social.media.aggregator.repository.UserInfluencerRepository;
import com.social.media.aggregator.repository.UserRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private InfluencerRepository influencerRepository;

	@Autowired
	private UserInfluencerRepository userInfluencerRepository;

	public User registerUser(UserCreationDTO user) {
		try {
			User newUser = new User();
			newUser.setPassword(Base64.getEncoder().encodeToString(user.getPassword().getBytes()));
			newUser.setUsername(user.getUsername());
			newUser.setLightmode(user.getLightMode());
			return userRepository.save(newUser);
		} catch (Exception e) {
			log.error("Exception occurred at registerUser at UserService : {}", ExceptionUtils.getStackTrace(e));
			throw new BusinessException(Constants.DEFAULT_ERROR_MESSAGE);
		}
	}

	public Optional<User> findByUsername(String username) {
		try {
			Optional<User> result = userRepository.findByUsername(username);
			if (result.isPresent())
				return result;
			else
				throw new BusinessException("User not found");
		} catch (BusinessException e) {
			log.error("BusinessException occurred at findByUsername at UserService : {}",
					ExceptionUtils.getStackTrace(e));
			throw new BusinessException(e.getMessage());
		} catch (Exception e) {
			log.error("Exception occurred at findByUsername at UserService : {}", ExceptionUtils.getStackTrace(e));
			throw new BusinessException(Constants.DEFAULT_ERROR_MESSAGE);
		}
	}

	public void followInfluencers(String username, List<String> uniquehashes) {
		try {
			// Find the user by username
			Optional<User> user = userRepository.findByUsername(username);

			if (user.isPresent()) {
				for (String uniquehash : uniquehashes) {
					// Find each influencer by uniquehash
					Influencer influencer = influencerRepository.findByUniquehash(uniquehash);

					if (influencer != null) {
						// Create the UserInfluencer relationship
						UserInfluencer userInfluencer = new UserInfluencer();
						userInfluencer.setUser(user.get());
						userInfluencer.setInfluencer(influencer);
						userInfluencerRepository.save(userInfluencer);
					} else {
						throw new BusinessException("Influencer not found");
					}
				}
			} else {
				throw new BusinessException("User not found");
			}
		} catch (BusinessException e) {
			log.error("BusinessException occurred at followInfluencers at UserService : {}",
					ExceptionUtils.getStackTrace(e));
			throw new BusinessException(e.getMessage());
		} catch (Exception e) {
			log.error("Exception occurred at followInfluencers at UserService : {}", ExceptionUtils.getStackTrace(e));
			throw new BusinessException(Constants.DEFAULT_ERROR_MESSAGE);
		}
	}
}
