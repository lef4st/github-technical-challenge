package br.trustly.challenge;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 * Test class responsible for testing the API endpoints, 
 * both in success and error cases
 *
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = ChallengeApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ApiTests extends BaseApiTest {

	public static final String URL_BASE_SUFFIX = "?repoUrl=";
	
	@Test
	public void getIndexPage() {
		
		/* Call the API */
		ResponseEntity<String> response = get("/", String.class);
		
		/* Verify returning HTTP code */
		assertEquals(HttpStatus.OK, response.getStatusCode());
		
		String title = response.getBody();
		
		/* Verify returning string */
		assertNotNull(title);
		assertEquals("API for scraping a github repository", title);
	}

	@Test
	public void wrongUrl() {
		
		String wrongUrl = "https://www.bing.com";
		String link = "/scrapRepoByUrl" + URL_BASE_SUFFIX + wrongUrl;
		
		/* Call the API */
		ResponseEntity response = get(link, String.class);
		
		/* Verify returning HTTP code */
		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
	}
	
	@Test
	public void defectiveUrl() {
		
		String defectiveUrl = "hps://github.com/lef4st/JAVA-MS-Compra-Transacao";
		String link = "/scrapRepoByUrl" + URL_BASE_SUFFIX + defectiveUrl;
		
		/* Call the API */
		ResponseEntity response = get(link, String.class);
		
		/* Verify returning HTTP code */
		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
	}
	
	@Test
	public void notFoundUrl() {
		
		String notFoundUrl = "https://github.com/lef4st/JAVA-MS";
		String link = "/scrapRepoByUrl" + URL_BASE_SUFFIX + notFoundUrl;
		
		/* Call the API */
		ResponseEntity response = get(link, String.class);
		
		/* Verify returning HTTP code */
		assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
	}
	
	@Test
	public void correctUrl() {
		
		String correctUrl = "https://github.com/lef4st/Weather-Forecast-SPA";
		String link = "/scrapRepoByUrl" + URL_BASE_SUFFIX + correctUrl;
		
		/* Call the API */
		ResponseEntity response = get(link, String.class);
		
		/* Verify returning HTTP code */
		assertEquals(HttpStatus.OK, response.getStatusCode());
	}
}
