package com.corneliadavis.cloudnative;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableAutoConfiguration
@SpringBootApplication
public class CloudnativeHelloworldApplication {

	public static void main(String[] args) {
		SpringApplication.run(CloudnativeHelloworldApplication.class, args);
	}
}
