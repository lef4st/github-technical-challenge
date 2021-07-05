package br.trustly.challenge.api.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Customization of error return for cases of subdirectory not found.
 *
 */
@ResponseStatus(code = HttpStatus.BAD_GATEWAY)
public class DirectoryNotFoundException extends RuntimeException {

	private static final long serialVersionUID = -1755650098471077780L;

	public DirectoryNotFoundException(String message) {
		super(message);
	}
	
	public DirectoryNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}
}
