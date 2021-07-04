package br.trustly.challenge.api.services.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import br.trustly.challenge.api.exceptions.DirectoryNotFoundException;
import br.trustly.challenge.api.models.Extension;
import br.trustly.challenge.api.models.FileData;
import br.trustly.challenge.api.models.GitHubRepo;
import br.trustly.challenge.api.services.ScrapingService;
import br.trustly.challenge.api.utils.GitHubUtils;
import br.trustly.challenge.api.utils.ParserUtils;

@Service
public class ScrapingServiceImpl implements ScrapingService {

	@Override
	@CacheEvict(value = "repositories", allEntries=true) 
	public void emptyCache() {}
	
	@Override
	@Cacheable(value="repositories", key ="#url")
	public GitHubRepo scrapRepoByUrlCacheable(String url) throws IOException {

		String commitCode = getFinalCommitCode(url);
		
		HashMap<String, Extension> extensionsMap = scrapDirectoryByUrl(url);

		GitHubRepo repository = new GitHubRepo();
		repository.setUrl(url);
		repository.setCommit(commitCode);
		repository.setExtensionsMap(extensionsMap);
		
		return repository;
	}
	
	@Override
	public HashMap<String, Extension> scrapDirectoryByUrl(String url) throws IOException {
		
		HashMap<String, Extension> extensionsMap = null;
		BufferedReader in;
		URL urlResource;

		urlResource = new URL(url);
		
		in = new BufferedReader(
				new InputStreamReader(urlResource.openStream()));
		
		try {

			// Get main items section
	        ParserUtils.getMainItensSection(in);
			
	        // Verify files and directories
	        extensionsMap = processFileListBySection(in);

		} finally {
			if(in != null) {
				in.close();
			}
		}
		
		return extensionsMap;
	}

	@Override
	public HashMap<String, Extension> processFileListBySection(BufferedReader in) throws IOException {
		
		HashMap<String, Extension> extensionsMap = new HashMap<>();
		ArrayList<String> directoryUrls = new ArrayList<String>();
		ArrayList<String> fileRawUrls = new ArrayList<String>();
		boolean insideDirectoryOrFileSection = true;
	
		while(insideDirectoryOrFileSection) {
		
			String DirectoryOrFile = ParserUtils.verifyNextItemIsDirectoryOrFile(in);
			
			if(DirectoryOrFile == null) {
				insideDirectoryOrFileSection = false;
				break;
			}
			
			switch(DirectoryOrFile) {
			
				case "Directory":
					
					String directoryUrl = ParserUtils.getDirectoryUrl(in);
					directoryUrls.add(directoryUrl);
					break;
					
				case "File":

					String fileRawUrl = ParserUtils.getFileRawUrl(in);
					fileRawUrls.add(fileRawUrl);
					break;
			}
		}
		
		in.close();
		
		fileRawUrls.parallelStream().forEach(fileRawUrl -> {
			
			HashMap<String, Extension> extensionsMapLoop = new HashMap<String, Extension>();
			FileData fileData = new FileData();
			
			try {
				fileData.calculateFileDataWithUrl(new URL(fileRawUrl));
			} catch (IOException e) {
				throw new DirectoryNotFoundException("File raw directory not found: " + fileRawUrl);
			}

			Extension extensionToAdd = new  Extension();
			extensionToAdd.setExtension(fileData.getExtension());
			extensionToAdd.setLines(fileData.getLines());
			extensionToAdd.setBytes(fileData.getBytes());
			
			extensionsMapLoop.put(fileData.getExtension(), extensionToAdd);
			
			GitHubUtils.sumExtensionsMaps(extensionsMap, extensionsMapLoop);
		});
		
		directoryUrls.parallelStream().forEach(directoryUrl -> {
			
			HashMap<String, Extension> extensionsMapLoop = new HashMap<String, Extension>();
			
			try {
				extensionsMapLoop = scrapDirectoryByUrl(directoryUrl);
			} catch (IOException e) {
				throw new DirectoryNotFoundException("Subdirectory not found: " + directoryUrl);
			}
			
			GitHubUtils.sumExtensionsMaps(extensionsMap, extensionsMapLoop);
		});
		
		
		return extensionsMap;
	}

	@Override
	public String getFinalCommitCode(String url) throws IOException {
		
		BufferedReader in;
		URL urlResource;
		String finalCommitCode = null;
		
		urlResource = new URL(url);
		
		in = new BufferedReader(
		new InputStreamReader(urlResource.openStream()));

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
