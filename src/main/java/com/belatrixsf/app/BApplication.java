package com.belatrixsf.app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BApplication implements CommandLineRunner {
	
	private static Logger LOGGER = LoggerFactory.getLogger(BApplication.class);

	public static void main(String[] args) {
		LOGGER.info("Init application");
		SpringApplication.run(BApplication.class, args);
		LOGGER.info("application finished");
	}

	@Override
	public void run(String... args) throws Exception {
	}
}
