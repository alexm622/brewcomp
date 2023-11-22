package com.brewcompanion.brewcomp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.brewcompanion.brewcomp.utils.Config;
import com.brewcompanion.brewcomp.utils.MySql;

import lombok.Getter;

@SpringBootApplication
public class BrewingCompainionFrontendApplication {

	@Getter
	private static Config config;

	public static void main(String[] args) {
		
		//print initializing to log4j
		System.out.println("Initializing MySql...");
		MySql.initialize();
		System.out.println("MySql initialized.");

		//read config file
		System.out.println("Reading config file...");
		try {
			config = Config.loadConfigFromFile("./config.json");
		} catch (Exception e) {
			System.err.println("Failed to read config file.");
			e.printStackTrace();
			System.exit(1);
		}
		System.out.println("Config file read.");

		SpringApplication.run(BrewingCompainionFrontendApplication.class, args);
	}

}
