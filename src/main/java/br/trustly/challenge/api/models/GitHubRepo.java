package br.trustly.challenge.api.models;

import java.util.HashMap;

/**
 * Contains key information about a repository
 *
 */
public class GitHubRepo {

	private String url;
	private String commit;
	private HashMap<String, Extension> extensionsMap;
	
	public GitHubRepo() {}

	public GitHubRepo(String url, String commit, HashMap<String, Extension> extensionsMap) {
		super();
		this.url = url;
		this.commit = commit;
		this.extensionsMap = extensionsMap;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getCommit() {
		return commit;
	}

	public void setCommit(String commit) {
		this.commit = commit;
	}

	public HashMap<String, Extension> getExtensionsMap() {
		return extensionsMap;
	}

	public void setExtensionsMap(HashMap<String, Extension> extensionsMap) {
		this.extensionsMap = extensionsMap;
	}
}
