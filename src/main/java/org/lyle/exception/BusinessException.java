package org.lyle.exception;

import java.io.Serial;

/**
 *
 */
public class BusinessException extends RuntimeException {

	@Serial
	private static final long serialVersionUID = -4880964474551876448L;

	public BusinessException() {
		super();
	}

	public BusinessException(String message) {
		super(message);
	}

	public BusinessException(String message, Throwable cause) {
		super(message, cause);
	}

	public BusinessException(Throwable cause) {
		super(cause);
	}
}