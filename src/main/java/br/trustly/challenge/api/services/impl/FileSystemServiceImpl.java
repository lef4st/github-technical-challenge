package br.trustly.challenge.api.services.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.trustly.challenge.api.exceptions.DirectoryNotFoundException;
import br.trustly.challenge.api.models.Extension;
import br.trustly.challenge.api.models.FileData;
import br.trustly.challenge.api.models.FileSystem;
import br.trustly.challenge.api.models.FileSystemNode;
import br.trustly.challenge.api.services.FileSystemService;
import br.trustly.challenge.api.services.ScrapingService;
import br.trustly.challenge.api.utils.GitHubUtils;
import br.trustly.challenge.api.utils.ParserUtils;

@Service
public class FileSystemServiceImpl implements FileSystemService {

	@Autowired
	ScrapingService scrapingService;
	
	@Override
	public ArrayList<FileSystemNode> processFileListBySection(BufferedReader in) throws IOException {

		ArrayList<FileSystemNode> fileSystemNodes = new ArrayList<FileSystemNode>();
		boolean insideDirectoryOrFileSection = true;
	
		// process the files and directories section
		while(insideDirectoryOrFileSection) {
		
			// checks if the next item is a directory or a file
			String DirectoryOrFile = ParserUtils.verifyNextItemIsDirectoryOrFile(in);
			
			// if null, it means that the file and directory section is finished.
			if(DirectoryOrFile == null) {
				insideDirectoryOrFileSection = false;
				break;
			}
			
			switch(DirectoryOrFile) {
			
				case "Directory":
					
					// if directory, perform the proper processing to retrieve its url
					String directoryUrl = ParserUtils.getDirectoryUrl(in);
					fileSystemNodes.add(new FileSystemNode(FileSystem.DIRECTORY, directoryUrl));
					break;
					
				case "File":

					// if file, perform the proper processing to retrieve its url
					String fileRawUrl = ParserUtils.getFileRawUrl(in);
					fileSystemNodes.add(new FileSystemNode(FileSystem.FILE, fileRawUrl));
					break;
			}
		}
		
		in.close();

		return fileSystemNodes;
	}
	
	@Override
	public HashMap<String, Extension> processFileSystemNodes(ArrayList<FileSystemNode> fileSystemNodes)
			throws IOException {
		
		HashMap<String, Extension> extensionsMap = new HashMap<>();
		
		// uses Java's parallelStream feature to process the filesystem list
		fileSystemNodes.parallelStream().forEach(fileSystemNode -> {
			
			HashMap<String, Extension> extensionsMapLoop = new HashMap<String, Extension>();
			
			switch(fileSystemNode.getFs()) {
			
				case DIRECTORY:
			
					// calls recursion to process the level below the directory
					try {
						extensionsMapLoop = scrapingService.scrapDirectoryByUrl(fileSystemNode.getLink());
					} catch (IOException e) {
						throw new DirectoryNotFoundException("Subdirectory not found: " 
								+ fileSystemNode.getLink());
					}
					
					break;
					
				case FILE:
					
					FileData fileData = new FileData();
					
					// calculates the data of a file
					try {
						fileData.calculateFileDataWithUrl(new URL(fileSystemNode.getLink()));
					} catch (IOException e) {
						throw new DirectoryNotFoundException("File raw directory not found: " 
								+ fileSystemNode.getLink());
					}

					Extension extensionToAdd = new  Extension();
					extensionToAdd.setExtension(fileData.getExtension());
					extensionToAdd.setLines(fileData.getLines());
					extensionToAdd.setBytes(fileData.getBytes());
					
					extensionsMapLoop.put(fileData.getExtension(), extensionToAdd);

					break;
			}
			
			// Sums the value of the current iteration with the values of the iterations already performed
			GitHubUtils.sumExtensionsMaps(extensionsMap, extensionsMapLoop);
		});	
		
		return extensionsMap;
	}
	
}
