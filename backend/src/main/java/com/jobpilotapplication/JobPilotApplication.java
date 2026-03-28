package com.jobpilotapplication;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableAsync
@EnableScheduling
public class JobPilotApplication {

	public static void main(String[] args) {
		SpringApplication.run(JobPilotApplication.class, args);
	}

}
