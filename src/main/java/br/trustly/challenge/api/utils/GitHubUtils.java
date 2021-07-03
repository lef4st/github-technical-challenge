package br.trustly.challenge.api.utils;

import java.util.HashMap;

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
}
