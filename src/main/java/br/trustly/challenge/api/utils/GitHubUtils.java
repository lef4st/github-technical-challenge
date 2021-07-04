package br.trustly.challenge.api.utils;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

import org.apache.commons.validator.routines.UrlValidator;

import br.trustly.challenge.api.models.Extension;

/**
 * General utility class
 *
 */
public class GitHubUtils {

	private GitHubUtils() {}
	
	/**
	 * Merge and add ExtensionsMaps
	 * 
	 * @param total ExtensionsMap with total accumulated value
	 * @param partial ExtensionsMap with partial value
	 * @return A <b>HashMap<String, Extension></b> with merged and updated values
	 */
	public static HashMap<String, Extension> sumExtensionsMaps(HashMap<String, Extension> total, HashMap<String, Extension> partial){
		
		partial.forEach((key, value) -> total.merge(key, value, (extensionTotal, extensionPartial) -> {
		
			extensionTotal.setCount(extensionTotal.getCount() + extensionPartial.getCount());
			extensionTotal.setBytes(extensionTotal.getBytes() + extensionPartial.getBytes());
			extensionTotal.setLines(extensionTotal.getLines() + extensionPartial.getLines());

			return extensionTotal;
		}));
		
		
		return total;
	}
	
	/**
	 * Validates if a url is valid and points to github
	 * 
	 * @param url url to be validated
	 * @throws MalformedURLException
	 */
	public static void validateGitHubUrl(String url) throws MalformedURLException {
		
		validateUrl(url);
		validateGitHubHostUrl(url);
	}
	
	/**
	 * Validates if a url is valid
	 * 
	 * @param url url to be validated
	 * @throws MalformedURLException
	 */
	public static void validateUrl(String url) throws MalformedURLException {
		
		String[] schemes = {"http","https"};
		
		UrlValidator urlValidator = new UrlValidator(schemes);
		
		if(!urlValidator.isValid(url)) {
			throw new MalformedURLException();
		}
	}
	
	/**
	 * Validates if a url points to github
	 * 
	 * @param url url to be validated
	 * @throws MalformedURLException
	 */
	public static void validateGitHubHostUrl(String url) throws MalformedURLException {
		
		URL testingUrl = new URL(url);
		
		if(!"github.com".equals(testingUrl.getHost())) {
			throw new MalformedURLException();
		}
	}
}
