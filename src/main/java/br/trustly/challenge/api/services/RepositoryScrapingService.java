package br.trustly.challenge.api.services;

import java.io.IOException;

import br.trustly.challenge.api.DTO.ExtensionsResponseDTO;
import br.trustly.challenge.api.models.GitHubRequest;

/**
 * Responsible for intermediate the controller's request
 *
 */
public interface RepositoryScrapingService {

	ExtensionsResponseDTO scrapRepo(GitHubRequest request) throws IOException;
}
