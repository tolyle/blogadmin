package org.lyle.blogadmin.exception;

import java.io.Serial;

/**
 *
 */
public class QiNiuException extends RuntimeException {

	@Serial
	private static final long serialVersionUID = -4880964474551876448L;

	public QiNiuException() {
		super();
	}

	public QiNiuException(String message) {
		super(message);
	}

	public QiNiuException(String message, Throwable cause) {
		super(message, cause);
	}

	public QiNiuException(Throwable cause) {
		super(cause);
	}
}