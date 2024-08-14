package com.social.media.aggregator.dto;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Schema(description = "DTO for creating an influencer with associated social media profiles")
public class InfluencerCreationDTO {

	@Schema(description = "Name of the influencer", example = "John Doe", required = true)
	private String name;

	@Schema(description = "List of social media profiles associated with the influencer", required = true)
	private List<PlatformRequestForInfluencer> profiles;
}
