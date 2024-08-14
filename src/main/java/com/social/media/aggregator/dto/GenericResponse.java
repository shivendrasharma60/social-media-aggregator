package com.social.media.aggregator.dto;

import java.io.Serializable;
import java.util.Collections;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Generic response structure used across the application")
public class GenericResponse implements Serializable {
	private static final long serialVersionUID = -4984957161022465722L;

	@Schema(description = "Status of the response (e.g., 1 for success, 0 for failure)", example = "1", required = true)
	private Integer status;

	@Schema(description = "Message providing additional details about the response", example = "Operation completed successfully", required = true)
	private String message;

	@Schema(description = "Data returned as part of the response, can be any object or collection", example = "{...}")
	private Object data;

	public GenericResponse(Integer status, String message) {
		this.status = status;
		this.message = message;
		this.data = Collections.emptyMap();
	}
}
