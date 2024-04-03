package com.medium.keycloakintegration.config;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

@Component
public class KeycloakClientRoleConverter implements Converter<Jwt, Collection<GrantedAuthority>> {

	private static final String ROLES = "roles";
	private static final String REALM_ACCESS = "realm_access";

	@Override
	public Collection<GrantedAuthority> convert(Jwt jwt) {
		Map<String, Object> realmAccess = Optional.ofNullable(jwt.getClaimAsMap(REALM_ACCESS))
				.orElseGet(() -> Collections.emptyMap());
		return Optional.ofNullable((List<?>) realmAccess.get(ROLES)).orElseGet(() -> Collections.emptyList()).stream()
				.map(Object::toString).map(SimpleGrantedAuthority::new).collect(Collectors.toList());
	}

}