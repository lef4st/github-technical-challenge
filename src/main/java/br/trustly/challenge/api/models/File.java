package br.trustly.challenge.api.models;

/**
 * Contains the main attributes of a file
 *
 */
public class File {

	private String extension;
	private Long lines;
	private Long bytes;
	
	public File() {}

	public File(String extension, Long lines, Long bytes) {
		super();
		this.extension = extension;
		this.lines = lines;
		this.bytes = bytes;
	}

	public String getExtension() {
		return extension;
	}

	public void setExtension(String extension) {
		this.extension = extension;
	}

	public Long getLines() {
		return lines;
	}

	public void setLines(Long lines) {
		this.lines = lines;
	}

	public Long getBytes() {
		return bytes;
	}

	public void setBytes(Long bytes) {
		this.bytes = bytes;
	}
	
}
