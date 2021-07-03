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

}
