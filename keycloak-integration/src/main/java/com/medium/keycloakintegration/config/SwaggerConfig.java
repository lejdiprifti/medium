package com.medium.keycloakintegration.config;

import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.OAuthFlow;
import io.swagger.v3.oas.annotations.security.OAuthFlows;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.security.SecuritySchemes;

@Configuration
@OpenAPIDefinition(security = { @SecurityRequirement(name = "bearer") })
@SecuritySchemes(value = {
		@SecurityScheme(name = "bearer", type = SecuritySchemeType.OAUTH2, 
				flows = @OAuthFlows(implicit = @OAuthFlow(authorizationUrl = "${security.authorization.url}"))) })
public class SwaggerConfig {

}