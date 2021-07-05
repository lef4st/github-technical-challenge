package br.trustly.challenge.api.services;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import br.trustly.challenge.api.models.Extension;
import br.trustly.challenge.api.models.FileSystemNode;

/**
 * Responsible for processing the FileSystem
 *
 */
public interface FileSystemService {

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
}
