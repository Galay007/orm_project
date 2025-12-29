package com.example.ORM_project;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class  OrmProjectApplication {
	private static final Logger log = LoggerFactory.getLogger(OrmProjectApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(OrmProjectApplication.class, args);
		log.info("Приложение запущено");
	}

}
