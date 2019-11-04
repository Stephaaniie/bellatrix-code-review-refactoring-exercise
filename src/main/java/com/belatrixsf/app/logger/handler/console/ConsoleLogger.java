package com.belatrixsf.app.logger.handler.console;

import java.util.logging.ConsoleHandler;

/**
 * Console logger handler
 * @author emilio.watemberg
 */
public class ConsoleLogger {
    public static ConsoleHandler getConsoleHandler(){
        return new ConsoleHandler();
    }
}
