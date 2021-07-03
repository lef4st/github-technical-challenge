package br.trustly.challenge;

import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;

/**
 * Configuration and facilitation class for making 
 * HTTP requests to the API during tests
 *
 */
@SpringBootTest(classes = ChallengeApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BaseApiTest {

	@Autowired
	protected TestRestTemplate rest;
	
	HttpHeaders getHeaders() {
		
		HttpHeaders headers = new HttpHeaders();
		
		return headers;
	}
	
	<T> ResponseEntity<T> post(String url, Object body, Class<T> responseType){
		
		HttpHeaders headers = getHeaders();
		
		return rest.exchange(url, POST, new HttpEntity<>(body, headers), responseType);
	}
	
	<T> ResponseEntity<T> get(String url, Class<T> responseType){
		
		HttpHeaders headers = getHeaders();
		
		return rest.exchange(url, GET, new HttpEntity<>(headers), responseType);
	}
}
