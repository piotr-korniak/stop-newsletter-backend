package com.stopnewsletter.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseAutoConfiguration;

@SpringBootApplication( exclude={
		DataSourceAutoConfiguration.class,
		LiquibaseAutoConfiguration.class })
public class StopNewsletterApplication {

	public static void main( String[] args) {
		SpringApplication.run( StopNewsletterApplication.class, args);
	}

}
