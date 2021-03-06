package br.trustly.challenge.api.exceptions;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * Exceptions configuration class, formalizing exceptions more adequately
 *
 */
@RestControllerAdvice
public class ExceptionConfig extends ResponseEntityExceptionHandler {

	@ExceptionHandler({
		MalformedURLException.class
	})
	public ResponseEntity<Object> errorBadRequest(Exception ex) {
		return new ResponseEntity<>(new Error("Malformed GitHub URL")
				, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler({
		FileNotFoundException.class
	})
	public ResponseEntity<Object> errorFileNotFound(Exception ex) {
		return new ResponseEntity<>(new Error("Url not found on GitHub")
				, HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler({
		IOException.class
	})
	public ResponseEntity<Object> errorBuffer(Exception ex) {
		return new ResponseEntity<>(new Error("Error processing information sent by GitHub")
				, HttpStatus.BAD_GATEWAY);
	}
	
	@Override
	protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(
			HttpRequestMethodNotSupportedException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
		
		return new ResponseEntity<>(new Error("Operation not allowed"), HttpStatus.METHOD_NOT_ALLOWED);
	}
}

class Error {
    public String error;

    public Error(String error) {
        this.error = error;
    }
}