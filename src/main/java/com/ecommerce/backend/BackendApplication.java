package com.ecommerce.backend;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;

@SpringBootApplication
public class BackendApplication {

	private static final Logger logger = LoggerFactory.getLogger(BackendApplication.class);

	public static void main(String[] args) {
		var context = SpringApplication.run(BackendApplication.class, args);
		Environment env = context.getEnvironment();

		logger.info("🚀 E-commerce Backend Application started successfully!");
		logger.info("✅ Active profiles: {}", String.join(", ", env.getActiveProfiles().length > 0 ? env.getActiveProfiles() : new String[]{"default"}));
		logger.info("🌐 Application running on port: {}", env.getProperty("server.port", "8080"));
	}
}
