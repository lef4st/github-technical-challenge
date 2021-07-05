package br.trustly.challenge.api.services.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import br.trustly.challenge.api.models.Extension;
import br.trustly.challenge.api.models.FileSystemNode;
import br.trustly.challenge.api.models.GitHubRepo;
import br.trustly.challenge.api.services.FileSystemService;
import br.trustly.challenge.api.services.ScrapingService;
import br.trustly.challenge.api.utils.ParserUtils;

/**
 * Implementation of the <b>ScrapingService</b> interface
 *
 */
@Service
public class ScrapingServiceImpl implements ScrapingService {

	@Autowired
	FileSystemService fileSystemService;
	
	@Override
	@CacheEvict(value = "repositories", allEntries=true) 
	public void emptyCache() {}
	
	@Override
	@Cacheable(value="repositories", key ="#url")
	public GitHubRepo scrapRepoByUrlCacheable(String url) throws IOException {

		// get the updated number of the last commit
		String commitCode = getFinalCommitCode(url);
		
		// starts the recursive scrap call on the repository
		HashMap<String, Extension> extensionsMap = scrapDirectoryByUrl(url);

		GitHubRepo repository = new GitHubRepo();
		repository.setUrl(url);
		repository.setCommit(commitCode);
		repository.setExtensionsMap(extensionsMap);
		
		return repository;
	}
	
	@Override
	public HashMap<String, Extension> scrapDirectoryByUrl(String url) throws IOException {
		
		ArrayList<FileSystemNode> fileSystemNodes = null;
		HashMap<String, Extension> extensionsMap = null;
		BufferedReader in;
		URL urlResource;

		urlResource = new URL(url);
		
		// buffers the html page
		in = new BufferedReader(
				new InputStreamReader(urlResource.openStream()));
		
		try {

			// Get main items section
	        ParserUtils.getMainItensSection(in);
			
	        // Verify files and directories
	        fileSystemNodes = fileSystemService.processFileListBySection(in);
	        
	        // Process files and directories
	        extensionsMap = fileSystemService.processFileSystemNodes(fileSystemNodes);

		} finally {
			if(in != null) {
				in.close();
			}
		}
		
		return extensionsMap;
	}

	@Override
	public String getFinalCommitCode(String url) throws IOException {
		
		BufferedReader in;
		URL urlResource;
		String finalCommitCode = null;
		
		urlResource = new URL(url);
		
		// buffers the html page
		in = new BufferedReader(
		new InputStreamReader(urlResource.openStream()));

		// get the number of the last commit
		try {
			finalCommitCode = ParserUtils.getFinalCommitCode(in);
		} finally {
			if(in != null) {
				in.close();
			}
		}
		
		return finalCommitCode;
	}

}
