package br.trustly.challenge.api.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.trustly.challenge.api.DTO.ExtensionsResponseDTO;
import br.trustly.challenge.api.models.GitHubRepo;
import br.trustly.challenge.api.models.GitHubRequest;
import br.trustly.challenge.api.services.RepositoryScrapingService;
import br.trustly.challenge.api.services.ScrapingService;

@Service
public class RepositoryScrapingServiceImpl implements RepositoryScrapingService {

	@Autowired
	ScrapingService scrapingService;
	
	@Override
	public ExtensionsResponseDTO scrapRepo(GitHubRequest request) {

		GitHubRepo repository = scrapingService.scrapRepoByUrl(request.getUrl());
		
		return ExtensionsResponseDTO.create(repository.getExtensionsMap());
	}

}
