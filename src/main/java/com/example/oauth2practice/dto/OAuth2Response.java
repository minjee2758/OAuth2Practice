package com.example.oauth2practice.dto;

public interface OAuth2Response {
	String getProvider();

	String getProviderId();

	String getEmail();

	String getName();
}
