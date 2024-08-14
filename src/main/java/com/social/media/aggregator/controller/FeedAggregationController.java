package com.social.media.aggregator.controller;

import java.util.List;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.social.media.aggregator.dto.GenericResponse;
import com.social.media.aggregator.service.FeedAggregationService;

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
@RequestMapping("/feeds")
@Tag(name = "Feed Aggregation", description = "APIs related to aggregating and retrieving social media feeds")
public class FeedAggregationController {

	@Autowired
	private FeedAggregationService feedAggregationService;

	@GetMapping("/aggregate")
	@Operation(summary = "Aggregate feeds", description = "Fetches aggregated social media feeds based on the user's followed influencers")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Feeds fetched successfully", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = GenericResponse.class)) }),
			@ApiResponse(responseCode = "400", description = "Invalid input", content = @Content),
			@ApiResponse(responseCode = "500", description = "Internal server error", content = @Content) })
	public ResponseEntity<GenericResponse> aggregateFeeds(
			@Parameter(description = "Username of the user", required = true) @RequestParam String username,
			@Parameter(description = "List of platforms to fetch feeds from", required = true) @RequestParam List<String> platforms) {
		try {
			return new ResponseEntity<>(GenericResponse.builder().status(1).message("Feeds fetched Successfully")
					.data(feedAggregationService.aggregateFeeds(username, platforms)).build(), HttpStatus.OK);
		} catch (Exception e) {
			log.error("Exception occurred at @FeedAggregationController : {}", ExceptionUtils.getStackTrace(e));
			return new ResponseEntity<>(GenericResponse.builder().status(0).message(e.getMessage()).data(null).build(),
					HttpStatus.OK);
		}
	}

	@GetMapping("/all")
	@Operation(summary = "Get all feeds", description = "Retrieves all social media feeds available in the system")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Feeds fetched successfully", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = GenericResponse.class)) }),
			@ApiResponse(responseCode = "500", description = "Internal server error", content = @Content) })
	public ResponseEntity<GenericResponse> getAllFeeds() {
		try {
			return new ResponseEntity<>(GenericResponse.builder().status(1).message("Feeds fetched Successfully")
					.data(feedAggregationService.getAllFeeds()).build(), HttpStatus.OK);
		} catch (Exception e) {
			log.error("Exception occurred at @FeedAggregationController : {}", ExceptionUtils.getStackTrace(e));
			return new ResponseEntity<>(GenericResponse.builder().status(0).message(e.getMessage()).data(null).build(),
					HttpStatus.OK);
		}
	}

	@GetMapping("/latest")
	@Operation(summary = "Get latest feeds", description = "Retrieves the latest social media feeds from specified platforms")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Feeds fetched successfully", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = GenericResponse.class)) }),
			@ApiResponse(responseCode = "400", description = "Invalid input", content = @Content),
			@ApiResponse(responseCode = "500", description = "Internal server error", content = @Content) })
	public ResponseEntity<GenericResponse> getLatestFeeds(
			@Parameter(description = "List of platforms to fetch the latest feeds from", required = true) @RequestParam List<String> platforms) {
		try {
			return new ResponseEntity<>(GenericResponse.builder().status(1).message("Feeds fetched Successfully")
					.data(feedAggregationService.getLatestFeeds(platforms)).build(), HttpStatus.OK);
		} catch (Exception e) {
			log.error("Exception occurred at @FeedAggregationController : {}", ExceptionUtils.getStackTrace(e));
			return new ResponseEntity<>(GenericResponse.builder().status(0).message(e.getMessage()).data(null).build(),
					HttpStatus.OK);
		}
	}
}
