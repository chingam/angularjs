package com.metafour.mtrak.router.exceptions;

/**
 * @author noor
 *
 */
public class MtrakRouterException extends Exception {

	private static final long serialVersionUID = -2500240221774718481L;

	public MtrakRouterException() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param message
	 */
	public MtrakRouterException(String message) {
		super(message);
	}

	/**
	 * @param cause
	 */
	public MtrakRouterException(Throwable cause) {
		super(cause);
	}

	/**
	 * @param message
	 * @param cause
	 */
	public MtrakRouterException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * @param message
	 * @param cause
	 * @param enableSuppression
	 * @param writableStackTrace
	 */
	public MtrakRouterException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}