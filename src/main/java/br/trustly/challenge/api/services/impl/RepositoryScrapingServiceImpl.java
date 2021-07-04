package br.trustly.challenge.api.services.impl;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.trustly.challenge.api.DTO.ExtensionsResponseDTO;
import br.trustly.challenge.api.models.GitHubRepo;
import br.trustly.challenge.api.models.GitHubRequest;
import br.trustly.challenge.api.services.RepositoryScrapingService;
import br.trustly.challenge.api.services.ScrapingService;
import br.trustly.challenge.api.utils.GitHubUtils;

@Service
public class RepositoryScrapingServiceImpl implements RepositoryScrapingService {

	@Autowired
	ScrapingService scrapingService;
	
	@Override
	public ExtensionsResponseDTO scrapRepo(GitHubRequest request) throws IOException {
		
		// validate url
		GitHubUtils.validateGitHubUrl(request.getUrl());
		
		// get final commit code
		String commitCode = scrapingService.getFinalCommitCode(request.getUrl());
	
		// if cached, get the cached response
		// if it's not, scrap the response
		GitHubRepo repository = scrapingService.scrapRepoByUrlCacheable(request.getUrl());
		
		// compare the final commit code with the response
		if(!commitCode.equals(repository.getCommit())) {
			
			// if the commit is out of date, empty the cache
			scrapingService.emptyCache();
			
			// scrap the response
			repository = scrapingService.scrapRepoByUrlCacheable(request.getUrl());
		}
		
		return ExtensionsResponseDTO.create(repository.getExtensionsMap());
	}

}
