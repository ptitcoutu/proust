package org.proust.core.model;

/**
 * This exception occurred while an invalid description is extracted from an event - ie description is null 
 * 
 * @author vincent.couturier@gmail.com
 *
 */
public class InvalidDescription extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public InvalidDescription() {
		super();
	}

	public InvalidDescription(String msg) {
		super(msg);
	}

	public InvalidDescription(Throwable cause) {
		super(cause);
	}

	public InvalidDescription(String msg, Throwable cause) {
		super(msg, cause);
	}

	public InvalidDescription(String msg, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(msg, cause, enableSuppression, writableStackTrace);
	}

}
