package com.kimhj.helloboot;

import static org.junit.Assert.assertTrue;

import java.lang.reflect.Type;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.kimhj.helloboot.board.vo.Board;
import com.kimhj.helloboot.response.ApiDataResponse;
import com.kimhj.helloboot.response.ApiResponse;
import com.kimhj.helloboot.response.error.ApiError;

@RunWith(SpringRunner.class)
// @SpringBootTest 서버를 띄울때 필요한 어노테이션
@SpringBootTest(webEnvironment=WebEnvironment.RANDOM_PORT)
public class HelloBootApplicationTests {

	private static String accessToken;
	
	@Value("${local.server.port}")
	private int port;
	
	private String host;
	
	private RestTemplate rest;
	private HttpHeaders headers;
	
	private Gson gson;
	
	// 딱 한번 사용
	@Before
	public static void settingAccessToken() {
		accessToken = "Bearer 22201e97-98c4-4e9d-b0b0-bfc90c0da7d0";
	}
	
	@Before
	public void setup() {
		host = "http://localhost:" + port;
		
		rest = new RestTemplate();
		gson = new Gson();
		headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		headers.add("Authorization", accessToken);
	}
			
	@Test
	public void createPost_Failed_NotEmpty_Both() {
		Board board = new Board();
		String requestJson = gson.toJson(board);
		/*
		 * {
		 * 	 "subject": null,
		 *   "content": null
		 * }
		 */
		
		HttpEntity<String> entity = new HttpEntity<>(requestJson, headers);	// body, header
		ResponseEntity<String> response = rest.exchange(
												host + "/posts"			// url
												, HttpMethod.POST		// method
												, entity				// header, body
												, String.class);		// respense type
		String responseBody = response.getBody();
		System.out.println(responseBody);
		
		ApiResponse apiResponse = gson.fromJson(responseBody, ApiResponse.class);	// responseBody를 ApiResponse로 변환
		
		assertTrue(apiResponse.getStatus().equals("Failed"));
		assertTrue(apiResponse.getError().size() == 2);
		for(ApiError error : apiResponse.getError()) {
			assertTrue(error.getCode().equals("1000"));
		}
	}
	
	@Test
	public void createPost_Failed_Length_Suject() {
		Board board = new Board();
		board.setSubject("abc");
		board.setContent("Content");
		String requestJson = gson.toJson(board);
		/*
		 * {
		 * 	 "subject": "abc",
		 *   "content": "Content"
		 * }
		 */
		
		HttpEntity<String> entity = new HttpEntity<>(requestJson, headers);	// body, header
		
		// Get 요청 시의 Http Request Message
		// HttpEntity<String> getEntity = new HttpEntity<>(headers);	// body X
		
		ResponseEntity<String> response = rest.exchange(
												host + "/posts"			// url
												, HttpMethod.POST		// method
												, entity				// header, body
												, String.class);		// respense type
		String responseBody = response.getBody();
		System.out.println(responseBody);
		
		ApiResponse apiResponse = gson.fromJson(responseBody, ApiResponse.class);	// json -> object
		
		assertTrue(apiResponse.getStatus().equals("Failed"));
		assertTrue(apiResponse.getError().size() == 1);
		for(ApiError error : apiResponse.getError()) {
			assertTrue(error.getCode().equals("1001"));
			assertTrue(error.getMessage().equals("subject prevent Length(10~100) policy."));
		}
	}

	@Test
	public void createPost_OK() {
		Board board = new Board();
		board.setSubject("Subject Test OK~~~");
		board.setContent("Content");
		String requestJson = gson.toJson(board);
		/*
		 * {
		 * 	 "subject": "Subject Test OK~~~",
		 *   "content": "Content"
		 * }
		 */
		
		HttpEntity<String> entity = new HttpEntity<>(requestJson, headers);	// body, header
		ResponseEntity<String> response = rest.exchange(
												host + "/posts"			// url
												, HttpMethod.POST		// method
												, entity				// header, body
												, String.class);		// respense type
		String responseBody = response.getBody();
		System.out.println(responseBody);
		
		/*
		 * Generic이 포함된 타입을 캐스팅하기 위해서는 Type 클래스 활용
		 * Generic 변환을 위한 Type 생성
		 */
		Type boardType = new TypeToken<ApiDataResponse<Board>>()
								{}.getType();
		ApiDataResponse<Board> apiResponse = 
						// gson.fromJson(responseBody, ApiDataResponse.class);	// json -> object
						gson.fromJson(responseBody, boardType);	// json -> object
		
		assertTrue(apiResponse.getStatus().equals("OK"));
		assertTrue(apiResponse.getError().size() == 0);
		
		Board responseBoard = apiResponse.getData();
		assertTrue(responseBoard.getSubject().equals(board.getSubject()));
		assertTrue(responseBoard.getContent().equals(board.getContent()));
	}
}
