package com.social.media.aggregator.exception;

public class BusinessException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6301042074194432698L;

	public BusinessException(String message) {
		super(message);
	}

	public BusinessException(Throwable t) {
		super(t);
	}

	public BusinessException(String message, Throwable e) {
		super(message, e);
	}

}
