package com.belatrixsf.app.logger.domain;

/**
 * Define levels WARN > ERROR > INFO
 * 
 * @author emilio.watemberg
 */
public class Level extends AbstractLevel {

	public Level(String name, int value) {
		this.setName(name);
		this.setValue(value);
	}

	public static final Level INFO = new Level("INFO", 1);

	public static final Level ERROR = new Level("ERROR", 2);

	public static final Level WARN = new Level("WARN", 3);

}
