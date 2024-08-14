package com.social.media.aggregator.service;

import com.social.media.aggregator.dto.UserCreationDTO;
import com.social.media.aggregator.entity.Influencer;
import com.social.media.aggregator.entity.User;
import com.social.media.aggregator.entity.UserInfluencer;
import com.social.media.aggregator.exception.BusinessException;
import com.social.media.aggregator.repository.InfluencerRepository;
import com.social.media.aggregator.repository.UserInfluencerRepository;
import com.social.media.aggregator.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

	@InjectMocks
	private UserService userService;

	@Mock
	private UserRepository userRepository;

	@Mock
	private InfluencerRepository influencerRepository;

	@Mock
	private UserInfluencerRepository userInfluencerRepository;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	public void testRegisterUser() {
		UserCreationDTO userDTO = new UserCreationDTO();
		userDTO.setUsername("user1");
		userDTO.setPassword("password1");

		User newUser = new User();
		newUser.setUsername(userDTO.getUsername());
		newUser.setPassword(userDTO.getPassword());

		when(userRepository.save(any(User.class))).thenReturn(newUser);

		User result = userService.registerUser(userDTO);

		assertNotNull(result);
		assertEquals("user1", result.getUsername());
		verify(userRepository, times(1)).save(any(User.class));
	}

	@Test
	public void testFollowInfluencers() {
		String username = "user1";
		User user = new User();
		user.setId(1L);
		user.setUsername(username);

		Influencer influencer1 = new Influencer();
		influencer1.setId(1L);
		influencer1.setName("Influencer 1");
		influencer1.setUniquehash("uniquehash1");

		Influencer influencer2 = new Influencer();
		influencer2.setId(2L);
		influencer2.setName("Influencer 2");
		influencer2.setUniquehash("uniquehash2");

		when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));
		when(influencerRepository.findByUniquehash("uniquehash1")).thenReturn(influencer1);
		when(influencerRepository.findByUniquehash("uniquehash2")).thenReturn(influencer2);

		userService.followInfluencers(username, Arrays.asList("uniquehash1", "uniquehash2"));

		verify(userInfluencerRepository, times(2)).save(any(UserInfluencer.class));
	}

	@Test
	public void testFollowInfluencers_UserNotFound() {
		String username = "userNotFound";
		when(userRepository.findByUsername(username)).thenReturn(Optional.empty());

		Exception exception = assertThrows(BusinessException.class, () -> {
			userService.followInfluencers(username, Arrays.asList("uniquehash1", "uniquehash2"));
		});

		assertEquals("User not found", exception.getMessage());
	}

	@Test
	public void testFollowInfluencers_InfluencerNotFound() {
		String username = "user1";
		User user = new User();
		user.setId(1L);
		user.setUsername(username);

		when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));
		when(influencerRepository.findByUniquehash("uniquehash1")).thenReturn(null);

		Exception exception = assertThrows(RuntimeException.class, () -> {
			userService.followInfluencers(username, Arrays.asList("uniquehash1", "uniquehash2"));
		});

		assertEquals("Influencer not found", exception.getMessage());
	}
}
