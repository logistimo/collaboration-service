package com.logistimo.social;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;

@SpringBootApplication(scanBasePackages = "com.logisitmo.*,com.logistimo.social.*")
@EnableHystrix
public class LogistimoSocialApplication {

	public static void main(String[] args) {
		SpringApplication.run(LogistimoSocialApplication.class, args);
	}
}
