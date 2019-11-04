package com.belatrixsf.app.logger.exception;

/**
 * Handler Exception
 * @author emilio.watemberg
 */
public class NotFoundHandlerException extends Exception {
	 
	private static final long serialVersionUID = 1L;

	public NotFoundHandlerException(String message) {
        super(message);
    }
}