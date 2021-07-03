package br.trustly.challenge.api.services;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;

import br.trustly.challenge.api.models.Extension;
import br.trustly.challenge.api.models.GitHubRepo;

/**
 * Responsible for scraping the repository, given a url
 *
 */
public interface ScrapingService {

	void emptyCache();
	
	GitHubRepo scrapRepoByUrlCacheable(String url);
	
	HashMap<String, Extension> scrapDirectoryByUrl(String url);
	
	HashMap<String, Extension> processFileListBySection(BufferedReader in) throws IOException;
	
	String getFinalCommitCode(String url) throws IOException;
}
