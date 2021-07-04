package br.trustly.challenge.api.utils;

import java.io.BufferedReader;
import java.io.IOException;

/**
 * Utility class focused on subjects involving parse
 *
 */
public class ParserUtils {

	public static final String URL_BASE_PREFIX = "https://github.com";
	public static final String URL_RAW_PREFIX = "https://raw.githubusercontent.com";
	
	private ParserUtils() {}
	
	
	/**
	 * Searches for the final commit number
	 * 
	 * @param in buffer with html page content
	 * @return the number of the last commit
	 * @throws IOException
	 */
	public static String getFinalCommitCode(BufferedReader in) throws IOException {
		
		String inputLine;
		String finalCommitCode = null;

		while ((inputLine = in.readLine()) != null) {
        	if(inputLine.contains("js-permalink-shortcut")) {
        		
        		String auxSubString = inputLine.substring(inputLine.indexOf("href=\"") + 6);
        		String link = auxSubString.substring(0, auxSubString.indexOf("\""));
        		finalCommitCode = link.substring(link.lastIndexOf('/') + 1);
        		
        		break;
        	}
        }
		
		return finalCommitCode;
	}
	
	/**
	 * Cycles through the buffer and returns the buffer pointed to the 
	 * section that corresponds to files and directories.
	 * 
	 * @param in buffer with html page content
	 * @throws IOException
	 */
	public static void getMainItensSection(BufferedReader in) throws IOException {
		
		String inputLine;
		
        while ((inputLine = in.readLine()) != null) {
        	if(inputLine.contains("aria-labelledby=\"files\"")) {
        		break;
        	}
        }
	}
	
	/**
	 * Checks if the next filesystem found in the buffer is file or directory
	 * 
	 * @param in buffer with html page content
	 * @return A String with the word directory or file
	 * @throws IOException
	 */
	public static String verifyNextItemIsDirectoryOrFile(BufferedReader in) throws IOException {
		
		String inputLine;
		
		while ((inputLine = in.readLine()) != null) {
			 
			//directory or file section already ended
			if(inputLine.contains("class=\"repo-file-upload-tree-target")) {
				 
				return null;
				 
			}else if(inputLine.contains("octicon-file-directory")) {
				 
				return "Directory";
				 
			}else if(inputLine.contains("octicon-file")) {
				 
				return "File";
			}
		}
	
		return null;
	}
	
	/**
	 * Returns the next link of type directory found in buffer
	 * 
	 * @param in buffer with html page content
	 * @return a link of type directory
	 * @throws IOException
	 */
	public static String getDirectoryUrl(BufferedReader in) throws IOException {
		
		String inputLine;
		String directoryUrl = "";
		
		while ((inputLine = in.readLine()) != null) {
			
			if(inputLine.contains("href=\"")) {
				
				String auxSubString = inputLine.substring(inputLine.indexOf("href=\"") + 6);
				String link = auxSubString.substring(0, auxSubString.indexOf("\""));
				
				directoryUrl = URL_BASE_PREFIX + link;
				break;
			}
		}
		
		return directoryUrl;
	}
	
	/**
	 * Returns the next link of type file found in buffer
	 * 
	 * @param in buffer with html page content
	 * @return a link of type file
	 * @throws IOException
	 */
	public static String getFileRawUrl(BufferedReader in) throws IOException {
		
		String inputLine;
		String fileRawUrl = "";
		
		while ((inputLine = in.readLine()) != null) {
			
			if(inputLine.contains("href=\"")) { 
				
				String auxSubString = inputLine.substring(inputLine.indexOf("href=\"") + 6);
				String link = auxSubString.substring(0, auxSubString.indexOf("\""));
				
				String rawLink = link.replaceFirst("\\/blob", "");
				
				fileRawUrl = URL_RAW_PREFIX + rawLink;
				break;
			}
		}
		
		return fileRawUrl;
	}
	
}
