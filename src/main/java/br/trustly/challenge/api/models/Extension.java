package br.trustly.challenge.api.models;

/**
 * Count the occurrence of a given file extension
 *
 */
public class Extension extends File {

	private long count = 1;

	public Extension() {}

	public long getCount() {
		return count;
	}

	public void setCount(long count) {
		this.count = count;
	}
	
}
