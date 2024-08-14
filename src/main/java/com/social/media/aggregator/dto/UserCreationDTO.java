package com.social.media.aggregator.dto;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Schema(description = "DTO for creating a new user")
public class UserCreationDTO {

	@Parameter(description = "Username for the new user", example = "john_doe", required = true)
	String username;

	@Parameter(description = "Password for the new user", example = "password123", required = true)
	String password;

	@Parameter(description = "This the mode that you want to view in either dark or light", example = "true", required = true)
	Boolean lightMode;

}
