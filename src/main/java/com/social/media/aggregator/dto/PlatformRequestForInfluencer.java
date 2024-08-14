package com.social.media.aggregator.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PlatformRequestForInfluencer {
	private String platform;

	private String profileUrl;

}
