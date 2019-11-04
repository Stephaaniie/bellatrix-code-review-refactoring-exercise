package com.belatrixsf.app.logger;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.belatrixsf.app.logger.exception.ConfigurationException;
import com.belatrixsf.app.logger.exception.NotFoundHandlerException;

@SpringBootTest
class JobLoggerTests {

		
	@Test
	void loggingWarningFileTest() throws Exception {
		Map<String,String> config = new HashMap<String,String>();
		config.put("logFileFolder", "logs");
		ILogger LOGGER = new JobLogger(true, false, false, false, true, false, config);
		LOGGER.logMessage("Example logging warning", true, false, false);	
		File logFile = new File("logs/logFile.txt");
		assertTrue(logFile.exists());
		logFile.deleteOnExit();
	}
	
	@Test
	void loggingNotFoundHandlerExceptionTest() throws Exception {
		assertThrows(NotFoundHandlerException.class, () -> {
			ILogger LOGGER = new JobLogger(false, false, false, false, false, false, null);
			LOGGER.logMessage("Example logging warning", true, false, false);	
	    });
	}
	
	@Test
	void loggingConfigurationExceptionTest() throws Exception {
		assertThrows(ConfigurationException.class, () -> {
			ILogger LOGGER = new JobLogger(false, true, false, true, false, false, null);
			LOGGER.logMessage("Example logging warning", false, false, false);	
	    });
	}
	
   

}
