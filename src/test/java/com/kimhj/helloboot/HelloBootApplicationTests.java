package com.kimhj.helloboot;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.HttpHeaders;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import com.google.gson.Gson;

@RunWith(SpringRunner.class)
// @SpringBootTest 서버를 띄울때 필요한 어노테이션
@SpringBootTest(webEnvironment=WebEnvironment.RANDOM_PORT)
public class HelloBootApplicationTests {

	@Value("${local.server.port}")
	private int port;
	
	private String host = "http://localhost:" + port;
	
	@Autowired
	private RestTemplate rest;
	private HttpHeaders headers;
	
	private Gson gson;
	
	@Before
	public void setup() {
		gson = new Gson();
		headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
	}
			
	@Test
	public void contextLoads() {
	}

}
