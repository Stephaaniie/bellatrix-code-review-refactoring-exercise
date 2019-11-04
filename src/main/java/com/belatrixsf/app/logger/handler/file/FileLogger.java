package com.belatrixsf.app.logger.handler.file;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.logging.FileHandler;

/**
 * File Logger Handler
 * @author emilio.watemberg
 */
public class FileLogger {
    private static Map dbParams;

    public static FileHandler getFileHandler(Map dbParamsMap) throws IOException {
        try {
	    	dbParams = dbParamsMap;
	        File logFile = new File(dbParams.get("logFileFolder") + "/logFile.txt");
	        if (!logFile.exists()) {
	            logFile.createNewFile();
	        }
	        FileHandler fh = new FileHandler(dbParams.get("logFileFolder") + "/logFile.txt");
	        return fh;
        } catch (IOException e){
        	e.printStackTrace();
        	throw e;
        }
    }
}