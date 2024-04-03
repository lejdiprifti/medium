package com.medium.keycloakintegration.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Autowired
	private KeycloakClientRoleConverter keycloakClientRoleConverter;

	@Bean
	public SecurityFilterChain configurePaths(HttpSecurity http,
			@Value("${security.authentication.unsecure.paths}") List<String> springSecurityAllowedPaths)
			throws Exception {
		AntPathRequestMatcher[] allowedPaths = springSecurityAllowedPaths.stream().map(AntPathRequestMatcher::new)
				.toArray(AntPathRequestMatcher[]::new);

		http.sessionManagement(
				sessionManagement -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
		http.authorizeHttpRequests(authorizeHttpRequests -> authorizeHttpRequests.requestMatchers(allowedPaths)
				.permitAll().anyRequest().authenticated());
		http.oauth2ResourceServer(
				oauth2 -> oauth2.jwt(jwt -> jwt.jwtAuthenticationConverter(jwtAuthenticationConverter())));

		return http.build();
	}

	private Converter<Jwt, ? extends AbstractAuthenticationToken> jwtAuthenticationConverter() {
		JwtAuthenticationConverter jwtConverter = new JwtAuthenticationConverter();
		jwtConverter.setJwtGrantedAuthoritiesConverter(keycloakClientRoleConverter);
		return jwtConverter;
	}

}
