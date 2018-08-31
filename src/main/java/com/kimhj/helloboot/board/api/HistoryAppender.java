package com.kimhj.helloboot.board.api;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.google.gson.Gson;

public class HistoryAppender {

	public void append(String url, String param, String response) {
		
		RestTemplate rest = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		
		Map<String, Object> requestMap = new HashMap<>();
		requestMap.put("timeMillies", System.currentTimeMillis()/1000);
		requestMap.put("url", url);
		requestMap.put("param", param);
		requestMap.put("response", response);
		
		Gson gson = new Gson();
		String requestJson = gson.toJson(requestMap);
		
		HttpEntity<String> entity = new HttpEntity<>(requestJson, headers);
		ResponseEntity<String> responseEntity = rest.exchange(
												"http://localhost:9090/histories"
												, HttpMethod.POST
												, entity
												, String.class);
		
		System.out.println("Response Entity >>>>>>>>>>>>>>>>>> " + responseEntity.getBody());
	}
}
