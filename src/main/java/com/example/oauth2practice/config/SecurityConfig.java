package com.example.oauth2practice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

import com.example.oauth2practice.service.CustomOAuth2UserService;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

	//security에 직접 만든 유저서비스를 등록하기 위해 선언
	private final CustomOAuth2UserService customOAuth2UserService;

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

		http
			.csrf((c)-> c.disable())
			.formLogin((login)-> login.disable())
			.httpBasic((httpBasic)-> httpBasic.disable())
			//OAuth2 설정
			.oauth2Login((outh2)-> outh2
				.userInfoEndpoint((userInfoEndpointConfig ->
					userInfoEndpointConfig.userService(customOAuth2UserService))))
			//인증&인가 범위 설정
			.authorizeHttpRequests((auth)-> auth
				.requestMatchers("/", "/oauth2/**", "/login/**").permitAll()
				.anyRequest().authenticated());
		return http.build();
	}
}
