package br.trustly.challenge.api.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * The controller <b>ScrapingController</b> has all the endpoints 
 * that will be used by the challenge
 * 
 * @author Leandro R. B. Pereira
 *
 */
@RestController
@RequestMapping("/")
public class ScrapingController {

	/**
	 * API Index, returns a greeting 
	 * 
	 * @return A <b>ResponseEntity</b> with status 200(OK) containing the greeting message
	 */
	@GetMapping
	public ResponseEntity<String> index() {
		return ResponseEntity.ok("API for scraping a github repository");
	}
	
}
