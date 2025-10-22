package com.vivitasol.projectbackend;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

import com.vivitasol.projectbackend.config.StorageProperties;
import com.vivitasol.projectbackend.services.StorageService;

@SpringBootApplication
@EnableConfigurationProperties(StorageProperties.class)
public class ProjectbackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProjectbackendApplication.class, args);
	}

	@Bean
	CommandLineRunner init(StorageService storageService) {
		return (args) -> {
			storageService.deleteAll();
			storageService.init();
		};
	}

}
