package com.example.oauth2practice.service;

import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.example.oauth2practice.dto.CustomOAuth2User;
import com.example.oauth2practice.dto.GoogleOAuth2Response;
import com.example.oauth2practice.dto.OAuth2Response;
import com.example.oauth2practice.entity.User;
import com.example.oauth2practice.repository.UserRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

	private final UserRepository userRepository;


	@Override
	@Transactional
	public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
		//부모 클래스에 존재하는 loadUser 메서드에 userRequest인자를 넣어 유저 정보를 가져온다
		OAuth2User oAuth2User = super.loadUser(userRequest);
		log.info("loadUser: {}", oAuth2User);

		//가져온 인증 정보가 구글인지, 네이버인지, 깃헙인지 알아내기 위한 변수
		String registrationId = userRequest.getClientRegistration().getRegistrationId();

		OAuth2Response oAuth2Response = null;

		//인증 정보를 제공하는 플랫폼마다 형식이 다르기 때문에 이에 맞게 가져와야한다.
		if (registrationId.equals("google")) {
			//인증 정보가 구글일 때
			//구글 dto구현체에서 정보 가져오기
			oAuth2Response = new GoogleOAuth2Response(oAuth2User.getAttributes());
		}
		// if (registrationId.equals("naver")) {
		// 	//인증 정보가 네이버일 때
		// }
		else {
			return null;
		}

		//유저 정보를 DB에 저장하기
		String username = oAuth2Response.getProvider()+" "+oAuth2Response.getProviderId();

		//이미 DB에 있는 유저인지 조회
		User user = userRepository.findUserByUsername(username);

		//DB에 없는 유저면
		if (user == null) {
			User newUser = new User(username, oAuth2Response.getEmail(), "ROLE_USER");
			userRepository.save(newUser);
		}
		//DB에 있으면 업데이트
		else {
			user.setEmail(oAuth2Response.getEmail());
			userRepository.save(user);
		}

		String role = "ROLE_USER";

		return new CustomOAuth2User(oAuth2Response, role);

	}
}
