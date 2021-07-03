package br.trustly.challenge.api.models;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

/**
 * Helps to calculate the main attributes of a file
 *
 */
public class FileData extends File {

	private URL url;
	
	public FileData() {}

public void calculateFileDataWithUrl(URL url) throws IOException {
		
		this.url = url;
		this.calculateFileExtension();
		this.calculateNumberOfBytes();
		this.calculateNumberOfLines();
	}
	
	public void calculateFileExtension() {
		
		String file = this.url.getFile();

	    if(file.contains(".")) {

	        String sub = file.substring(file.lastIndexOf('.') + 1);

	        if(sub.length() == 0) {
	            this.setExtension("");
	            return;
	        }

	        if(sub.contains("/")) {
	        	this.setExtension("");
	            return;
	        }
	        
	        if(sub.contains("?")) {
	        	this.setExtension(sub.substring(0, sub.indexOf('?')));
	        	return;
	        }

	        this.setExtension(sub);
	        return;
	    }

	    this.setExtension("");
	}
	
	public void calculateNumberOfLines() throws IOException {
		
		Long lines = 0L;
		
		BufferedReader in = new BufferedReader(
        new InputStreamReader(this.url.openStream()));

        while (in.readLine() != null) lines++;
        
        in.close();
		
        this.setLines(lines);
	}
	
	public void calculateNumberOfBytes() throws IOException {
		
		Long size = 0L;
		URLConnection conn;
		
		conn = this.url.openConnection();
		size = conn.getContentLengthLong();
		conn.getInputStream().close();
		
		this.setBytes(size);
	}
	
	public URL getUrl() {
		return url;
	}

	public void setUrl(URL url) {
		this.url = url;
	}
	
}
