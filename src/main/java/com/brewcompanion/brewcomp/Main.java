package com.brewcompanion.brewcomp;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import com.brewcompanion.brewcomp.utils.Config;
import com.brewcompanion.brewcomp.utils.minio.Minio;
import com.brewcompanion.brewcomp.utils.mysql.DatabaseInitializer;
import com.brewcompanion.brewcomp.utils.mysql.MySql;


import lombok.Getter;

@SpringBootApplication
public class Main {

	@Getter
	private static Config config;

	@Getter
	private static final Logger logger = LogManager.getLogger(Main.class);

	public static void main(String[] args) {

		SpringApplication app = new SpringApplication(Main.class);
		ConfigurableApplicationContext context = app.run(args);
		context.registerShutdownHook();

		Runtime.getRuntime().addShutdownHook(new Thread(() -> {
			// Your custom shutdown procedures here
			logger.info("Executing shutdown procedures...");
			shutdown();
		}));

		try {
			Thread.sleep(10);
		} catch (Exception e) {
			Thread.currentThread().interrupt();
			e.printStackTrace();
			System.exit(1);
		}

		// read config file
		logger.info("Reading config file...");
		try {
			config = Config.loadConfigFromFile("./config.json");
		} catch (Exception e) {
			Main.getLogger().error("Failed to read config file.");
			// check to see if the config file exists,
			// if it doesn't, generate it
			try {
				Config.generateConfigFile("./config.json");
				logger.info("Config file generated.");
				config = Config.loadConfigFromFile("./config.json");
			} catch (Exception e1) {
				Main.getLogger().error("Failed to generate config file.");
				e.printStackTrace();
				System.exit(1);
				e1.printStackTrace();
				System.exit(1);
			}

		}
		logger.info("Config file read.");

		try {
			// intialize mysql
			logger.info("Initializing MySql...");
			MySql.initialize();
			DatabaseInitializer.initialize();
			logger.info("MySql initialized.");
			
			// TODO remove this
			//MySqlDebug.resetAll();
		} catch (Exception e) {
			Main.getLogger().error("Failed to initialize MySql.");
			e.printStackTrace();
		}
		// initialize minio
		logger.info("Initializing Minio...");
		try {
			Minio.initialize();
			logger.info("Minio initialized.");
		} catch (Exception e) {
			Main.getLogger().error("Failed to initialize Minio.");
			e.printStackTrace();
		}
	}

	private static void shutdown() {
		// shutdown procedures
		logger.info("Shutting down...");
		try {
			MySql.shutdown();
			logger.info("MySql shutdown.");
		} catch (Exception e) {
			Main.getLogger().error("Failed to shutdown MySql.");
			e.printStackTrace();
		}
		logger.info("Shutdown complete.");
	}	
}
