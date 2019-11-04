package com.belatrixsf.app.logger.exception;

/**
 * Configuration Exception
 * @author emilio.watemberg
 */
public class ConfigurationException extends Exception {
	 
	private static final long serialVersionUID = 1L;

	public ConfigurationException(String message) {
        super(message);
    }
}