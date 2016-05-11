package org.proust.core.model;

/**
 * This exception occurred while trying to call an operation which required a specific data without this data 
 * 
 * @author vincent.couturier@gmail.com
 *
 */
public class UnspecifiedData extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public UnspecifiedData() {
		super();
	}

	public UnspecifiedData(String msg) {
		super(msg);
	}

	public UnspecifiedData(Throwable cause) {
		super(cause);
	}

	public UnspecifiedData(String msg, Throwable cause) {
		super(msg, cause);
	}

	public UnspecifiedData(String msg, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(msg, cause, enableSuppression, writableStackTrace);
	}

}
