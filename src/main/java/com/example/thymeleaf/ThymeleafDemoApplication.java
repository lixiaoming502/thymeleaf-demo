package com.example.thymeleaf;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
@EnableScheduling
@MapperScan("com.example.thymeleaf.dao")
public class ThymeleafDemoApplication {
	private static Log logger = LogFactory.getLog(ThymeleafDemoApplication.class);


	public static void main(String[] args) {
		logger.info("ThymeleafDemoApplication V"+Version.version+" start...");
		SpringApplication.run(ThymeleafDemoApplication.class, args);
	}
}
