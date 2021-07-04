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
	
	public static HashMap<String, Extension> sumExtensionsMaps(HashMap<String, Extension> total, HashMap<String, Extension> partial){
		
		partial.forEach((key, value) -> total.merge(key, value, (extensionTotal, extensionPartial) -> {
		
			extensionTotal.setCount(extensionTotal.getCount() + extensionPartial.getCount());
			extensionTotal.setBytes(extensionTotal.getBytes() + extensionPartial.getBytes());
			extensionTotal.setLines(extensionTotal.getLines() + extensionPartial.getLines());

			return extensionTotal;
		}));
		
		
		return total;
	}
	
	public static void validateGitHubUrl(String url) throws MalformedURLException {
		
		validateUrl(url);
		validateGitHubHostUrl(url);
	}
	
	public static void validateUrl(String url) throws MalformedURLException {
		
		String[] schemes = {"http","https"};
		
		UrlValidator urlValidator = new UrlValidator(schemes);
		
		if(!urlValidator.isValid(url)) {
			throw new MalformedURLException();
		}
	}
	
	public static void validateGitHubHostUrl(String url) throws MalformedURLException {
		
		URL testingUrl = new URL(url);
		
		if(!"github.com".equals(testingUrl.getHost())) {
			throw new MalformedURLException();
		}
	}
}
