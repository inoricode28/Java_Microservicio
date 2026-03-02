package com.example.Java3;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class Java3Application {

	public static void main(String[] args) {
		SpringApplication.run(Java3Application.class, args);
	}

}
