package com.social.media.aggregator.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.social.media.aggregator.dto.GenericResponse;
import com.social.media.aggregator.dto.InfluencerCreationDTO;
import com.social.media.aggregator.entity.Influencer;
import com.social.media.aggregator.service.InfluencerService;

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
@RequestMapping("/influencers")
@Tag(name = "Influencer Management", description = "APIs related to Influencer Management")
public class InfluencerController {

	@Autowired
	private InfluencerService influencerService;

	@PostMapping
	@Operation(summary = "Register a new influencer", description = "Creates a new influencer with associated social media profiles")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Influencer registered successfully", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = GenericResponse.class)) }),
			@ApiResponse(responseCode = "400", description = "Invalid input", content = @Content),
			@ApiResponse(responseCode = "500", description = "Internal server error", content = @Content) })
	public ResponseEntity<GenericResponse> addInfluencer(
			@Parameter(description = "Influencer details including social media profiles", required = true) @RequestBody InfluencerCreationDTO influencer) {
		try {
			Influencer addedInfluencer = influencerService.addInfluencer(influencer);
			return new ResponseEntity<>(GenericResponse.builder().status(1)
					.message("Influencer Registered Successfully").data(addedInfluencer.getId()).build(),
					HttpStatus.OK);
		} catch (Exception e) {
			log.error("Exception occurred at @InfluencerController : {}", ExceptionUtils.getStackTrace(e));
			return new ResponseEntity<>(GenericResponse.builder().status(0).message(e.getMessage()).data(null).build(),
					HttpStatus.OK);
		}
	}

	@GetMapping
	@Operation(summary = "Get all influencers", description = "Retrieves a list of all registered influencers")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Influencers fetched successfully", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = GenericResponse.class)) }),
			@ApiResponse(responseCode = "500", description = "Internal server error", content = @Content) })
	public ResponseEntity<GenericResponse> getAllInfluencers() {
		try {
			List<Influencer> influencers = influencerService.getAllInfluencers();
			Map<String, String> result = new HashMap<>();
			List<Map<String, Object>> influencerDetails = influencers.stream().map(influencer -> {
				Map<String, Object> map = new HashMap<>();
				map.put("influencerName", influencer.getName());
				map.put("uuid", influencer.getUniquehash());
				return map;
			}).collect(Collectors.toList());
			return new ResponseEntity<>(GenericResponse.builder().status(1).message("Influencers fetched Successfully")
					.data(influencerDetails).build(), HttpStatus.OK);
		} catch (Exception e) {
			log.error("Exception occurred at @InfluencerController : {}", ExceptionUtils.getStackTrace(e));
			return new ResponseEntity<>(GenericResponse.builder().status(0).message(e.getMessage()).data(null).build(),
					HttpStatus.OK);
		}
	}
}
