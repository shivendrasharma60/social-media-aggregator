package com.social.media.aggregator.controller;

import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.social.media.aggregator.dto.GenericResponse;
import com.social.media.aggregator.dto.UserCreationDTO;
import com.social.media.aggregator.entity.User;
import com.social.media.aggregator.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/users")
@Tag(name = "User Management", description = "APIs related to User Management")
public class UserController {

	@Autowired
	private UserService userService;

	@PostMapping("/register")
	@Operation(summary = "Register a new user", description = "Provide username and password to create a new user")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "User registered successfully", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = GenericResponse.class)) }),
			@ApiResponse(responseCode = "400", description = "Invalid input", content = @Content),
			@ApiResponse(responseCode = "500", description = "Internal server error", content = @Content) })
	public ResponseEntity<GenericResponse> registerUser(
			@Parameter(description = "User details for registration", required = true) @RequestBody UserCreationDTO user) {
		try {
			User registeredUser = userService.registerUser(user);
			return new ResponseEntity<>(GenericResponse.builder().status(1).message("User Registered Successfully")
					.data(registeredUser.getId()).build(), HttpStatus.OK);
		} catch (Exception e) {
			log.error("Exception occurred while registering user : {}", ExceptionUtils.getStackTrace(e));
			return new ResponseEntity<>(GenericResponse.builder().status(0).message(e.getMessage()).data(null).build(),
					HttpStatus.OK);
		}
	}

	@GetMapping("/{username}")
	@Operation(summary = "Get user by username", description = "Retrieve user details by username")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "User fetched successfully", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = GenericResponse.class)) }),
			@ApiResponse(responseCode = "400", description = "User not found", content = @Content),
			@ApiResponse(responseCode = "500", description = "Internal server error", content = @Content) })
	public ResponseEntity<GenericResponse> getUserByUsername(
			@Parameter(description = "Username of the user", required = true) @PathVariable String username) {
		try {
			Optional<User> user = userService.findByUsername(username);
			return new ResponseEntity<>(
					GenericResponse.builder().status(1).message("User fetched Successfully").data(user.get()).build(),
					HttpStatus.OK);
		} catch (Exception e) {
			log.error("Exception occurred while fetching user : {}", ExceptionUtils.getStackTrace(e));
			return new ResponseEntity<>(GenericResponse.builder().status(0).message(e.getMessage()).data(null).build(),
					HttpStatus.OK);
		}
	}

	@PostMapping("/{userId}/follow")
	@Operation(summary = "Follow influencers", description = "Allows a user to follow multiple influencers by providing their unique hashes")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "User followed influencers successfully", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = GenericResponse.class)) }),
			@ApiResponse(responseCode = "400", description = "Invalid input", content = @Content),
			@ApiResponse(responseCode = "500", description = "Internal server error", content = @Content) })
	public ResponseEntity<GenericResponse> followInfluencers(
			@Parameter(description = "Username of the user", required = true) @RequestParam String username,
			@Parameter(description = "List of unique hashes of influencers to follow", required = true) @RequestBody List<String> uniquehashes) {
		try {
			userService.followInfluencers(username, uniquehashes);
			return new ResponseEntity<>(GenericResponse.builder().status(1)
					.message("User followed influencers Successfully").data(null).build(), HttpStatus.OK);
		} catch (Exception e) {
			log.error("Exception occurred while following influencers : {}", ExceptionUtils.getStackTrace(e));
			return new ResponseEntity<>(GenericResponse.builder().status(0).message(e.getMessage()).data(null).build(),
					HttpStatus.OK);
		}
	}
}
