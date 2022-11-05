package com.example.corona.virus.tracker;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class CoronaVirusTrackerApplication {

	private static final Logger LOGGER = LoggerFactory.getLogger(CoronaVirusTrackerApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(CoronaVirusTrackerApplication.class, args);
		long startTime = new Date().getTime();
		long endTime = new Date().getTime();
		long runTime = endTime - startTime;
		LOGGER.info("CoronaVirusTracker Application Started " + startTime);
		LOGGER.info("CoronaVirusTracker Application Running " + runTime);
		LOGGER.info("CoronaVirusTracker Application Stopped " + endTime);

	}

}
