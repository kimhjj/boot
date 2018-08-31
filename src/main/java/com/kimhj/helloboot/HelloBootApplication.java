package com.kimhj.helloboot;


import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;
import org.springframework.web.client.ResourceAccessException;

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
public class HelloBootApplication extends ResourceServerConfigurerAdapter {

	public static void main(String[] args) {
		SpringApplication.run(HelloBootApplication.class, args);
	}
	
	@Override
	public void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
				.antMatchers("/member/**").permitAll()
				.antMatchers("/posts/**").authenticated();
	}
	
	@Bean
	public TokenStore jdbcTokenStore(DataSource dataSource) {
		return new JdbcTokenStore(dataSource);
	}
	
}
