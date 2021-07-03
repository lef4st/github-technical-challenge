package br.trustly.challenge.api.services.impl;

import java.io.IOException;

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

		//get final commit code
		String commitCode = null;
		try {
			commitCode = scrapingService.getFinalCommitCode(request.getUrl());
		} catch (IOException e) {
			e.printStackTrace();
		}
	
		// if cached, get the cached response
		// if it's not, scrap the response
		GitHubRepo repository = scrapingService.scrapRepoByUrlCacheable(request.getUrl());
		
		// compare the final commit code with the response
		if(!commitCode.equals(repository.getCommit())) {
			scrapingService.emptyCache();
			repository = scrapingService.scrapRepoByUrlCacheable(request.getUrl());
		}
		
		return ExtensionsResponseDTO.create(repository.getExtensionsMap());
	}

}
