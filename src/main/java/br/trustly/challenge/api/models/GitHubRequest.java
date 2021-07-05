package br.trustly.challenge.api.models;

/**
 * Contains the information of a given request
 *
 */
public class GitHubRequest {

	private String url;

	public GitHubRequest() {}

	public GitHubRequest(String url) {
		super();
		this.url = url;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
}
