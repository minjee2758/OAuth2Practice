package com.example.oauth2practice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class OAuth2PracticeApplication {

	public static void main(String[] args) {
		SpringApplication.run(OAuth2PracticeApplication.class, args);
	}

}
