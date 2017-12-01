package com.logistimo.collaboration;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;

@SpringBootApplication(scanBasePackages = "com.logisitmo.*,com.logistimo.collaboration.*")
@EnableHystrix
public class LogistimoSocialApplication {

	public static void main(String[] args) {
		SpringApplication.run(LogistimoSocialApplication.class, args);
	}
}
