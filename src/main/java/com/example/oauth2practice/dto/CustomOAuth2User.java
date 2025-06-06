package com.example.oauth2practice.dto;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CustomOAuth2User implements OAuth2User {

	private final OAuth2Response oAuth2Response;
	private final String role;

	//로그인을 진행하면 서버로부터 넘어오는 모든 데이터 값들
	@Override
	public Map<String, Object> getAttributes() {
		return Map.of();
	}

	//Role
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Collection<GrantedAuthority> authorities = new ArrayList<>();
		authorities.add(new GrantedAuthority() {
			@Override
			public String getAuthority() {
				return role;
			}
		});
		return authorities;
	}

	@Override
	public String getName() {
		return oAuth2Response.getName();
	}

	public String getUsername(){
		return oAuth2Response.getProvider()+" "+oAuth2Response.getProviderId();
	}
}
