package com.belatrixsf.app.logger;

import java.io.IOException;
import java.sql.SQLException;

import com.belatrixsf.app.logger.exception.ConfigurationException;
import com.belatrixsf.app.logger.exception.NotFoundHandlerException;

/**
 * Logger Inteface
 * 
 * @author emilio.watemberg
 *
 */
public interface ILogger {

	boolean isInfoEnabled();

	void info(String message);

	boolean isWarnEnabled();

	void warn(String message);

	boolean isErrorEnabled();

	void error(String message);

	void logMessage(String messageText, Boolean message, Boolean warning, Boolean error)
			throws NotFoundHandlerException, ConfigurationException, SQLException, IOException;

}
