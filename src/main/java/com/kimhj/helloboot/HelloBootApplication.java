package com.kimhj.helloboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

// @EnableResourceServer
// 모든 API가 OAuth 인증을 하도록 함
@EnableResourceServer

// @EnableAuthorizationServer
// OAuth2 인증 처리를 담당하는 Annotation
// URL 자동 생성: /oauth/token, /oauth/authorize
//      (1) /oauth/authorize => Client 인증 (client id, secret key)
//      (2) /oauth/token => API 접근 인증 (Access Token 발급)
// 둘다 받아야 서버 동작함
@EnableAuthorizationServer
@SpringBootApplication
public class HelloBootApplication {

	public static void main(String[] args) {
		SpringApplication.run(HelloBootApplication.class, args);
	}
}
