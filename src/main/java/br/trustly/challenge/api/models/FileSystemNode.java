package br.trustly.challenge.api.models;

public class FileSystemNode {

	private FileSystem fs;
	private String link;
	
	public FileSystemNode() {}

	public FileSystemNode(FileSystem fs, String link) {
		super();
		this.fs = fs;
		this.link = link;
	}

	public FileSystem getFs() {
		return fs;
	}

	public void setFs(FileSystem fs) {
		this.fs = fs;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}
	
}
