package com.splashScore.waterpolo_app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class SplashScoreApplication {

	public static void main(String[] args) {
		SpringApplication.run(SplashScoreApplication.class, args);
	}

}
