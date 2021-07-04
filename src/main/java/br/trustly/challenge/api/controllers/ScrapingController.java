package br.trustly.challenge.api.controllers;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.trustly.challenge.api.DTO.ExtensionsResponseDTO;
import br.trustly.challenge.api.models.Extension;
import br.trustly.challenge.api.models.GitHubRequest;
import br.trustly.challenge.api.services.RepositoryScrapingService;


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

	@Autowired
	private RepositoryScrapingService repoScrapingService;
	
	/**
	 * API Index, returns a greeting 
	 * 
	 * @return A <b>ResponseEntity</b> with status 200(OK) containing the greeting message
	 */
	@GetMapping
	public ResponseEntity<String> index() {
		return ResponseEntity.ok("API for scraping a github repository");
	}
	
	/**
	 *  Scraps a repository and returns the file count, the total number of lines 
	 *  and the total number of bytes grouped by file extension, 
	 *  of a given public GitHub repository
	 * 
	 * @param repoUrl url of the repository
	 * 
	 * @return A <b>ResponseEntity<List<Extension>></b> with status 200(OK) containing
	 *  a list with file count, the total number of lines and the total number of bytes 
	 *  grouped by file extension.
	 * @throws IOException 
	 */
	@GetMapping("/scrapRepoByUrl")
	public ResponseEntity<List<Extension>> scrapRepoByUrl(@RequestParam(value = "repoUrl", 
			required = true) String repoUrl) throws IOException {

		GitHubRequest request = new GitHubRequest(repoUrl);
		
		ExtensionsResponseDTO  responseDTO = repoScrapingService.scrapRepo(request);
		
		return ResponseEntity.ok(responseDTO.getData());
	}
	
}
