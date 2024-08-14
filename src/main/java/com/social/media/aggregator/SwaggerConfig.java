package com.social.media.aggregator;

import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@Configuration
@OpenAPIDefinition(info = @Info(title = "Swagger for Aggregator Service", description = "API documentation for aggregator service.", version = "1.0.0"), security = @SecurityRequirement(name = "Authorization"))
public class SwaggerConfig implements WebMvcConfigurer {

	@Bean
	public GroupedOpenApi publicApi() {
		return GroupedOpenApi.builder().group("aggregator-service").pathsToMatch("/**").build();
	}
}
