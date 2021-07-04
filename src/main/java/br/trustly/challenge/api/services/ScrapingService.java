package br.trustly.challenge.api.services;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import br.trustly.challenge.api.models.Extension;
import br.trustly.challenge.api.models.FileSystemNode;
import br.trustly.challenge.api.models.GitHubRepo;

/**
 * Responsible for scraping the repository, given a url
 *
 */
public interface ScrapingService {

	/**
	 * Empty API cache
	 */
	void emptyCache();
	
	/**
	 * Receive the url and run the entire repository scraping process.
	 * The result of this method is then cached.
	 * 
	 * @param url url of the repository to be scraped
	 * 
	 * @return a <b>GitHubRepo</b> containing the url, the number of the 
	 * last commit and the HashMap with the grouped extensions and values
	 *  
	 * @throws IOException
	 */
	GitHubRepo scrapRepoByUrlCacheable(String url) throws IOException;
	
	/**
	 * Method that receives the url of a directory and processes 
	 * the information recursively.
	 * 
	 * @param url url of the directory to be scraped
	 * 
	 * @return a <b>HashMap<String, Extension></b> with the grouped 
	 *  extensions and values
	 * @throws IOException
	 */
	HashMap<String, Extension> scrapDirectoryByUrl(String url) throws IOException;
	
	/**
	 * Processes the filesystem section of the page, grouping them 
	 * into a list of files and directories.
	 * 
	 * @param in buffer with the main section html page content
	 * 
	 * @return a <b>ArrayList<FileSystemNode></b> containing the 
	 *  filesystem nodes, which has the type and url
	 * @throws IOException
	 */
	ArrayList<FileSystemNode> processFileListBySection(BufferedReader in) throws IOException;
	
	/**
	 * Processes the list of filesystem nodes, wrapping the result 
	 * into a HashMap<String, Extension>
	 * 
	 * @param fileSystemNodes list of filesystem nodes
	 * 
	 * @return a <b>HashMap<String, Extension></b> with the grouped
	 *  extensions and values
	 * @throws IOException
	 */
	HashMap<String, Extension> processFileSystemNodes(ArrayList<FileSystemNode> fileSystemNodes) throws IOException;
	
	/**
	 * Retrieves the final commit number
	 * 
	 * @param url url of the repository to be scraped
	 * 
	 * @return the number of the last commit
	 * @throws IOException
	 */
	String getFinalCommitCode(String url) throws IOException;
}
