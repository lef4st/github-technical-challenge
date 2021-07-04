package br.trustly.challenge.api.services;

import java.io.IOException;

import br.trustly.challenge.api.DTO.ExtensionsResponseDTO;
import br.trustly.challenge.api.models.GitHubRequest;

/**
 * Responsible for intermediate the controller's request
 *
 */
public interface RepositoryScrapingService {

	/**
	 * Receives the request from the controller and starts the scraping process. 
	 * Then it returns the response already in DTO format
	 * 
	 * @param request request that contains the url of the repository to be scraped
	 * 
	 * @return a <b>ExtensionsResponseDTO</b> containing a list with file count, 
	 *  the total number of lines and the total number of bytes grouped 
	 *  by file extension
	 * @throws IOException
	 */
	ExtensionsResponseDTO scrapRepo(GitHubRequest request) throws IOException;
}
