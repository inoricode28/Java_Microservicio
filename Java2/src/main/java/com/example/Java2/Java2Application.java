package com.example.Java2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class Java2Application {

	public static void main(String[] args) {
		SpringApplication.run(Java2Application.class, args);
	}

}
