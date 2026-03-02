package com.example.Java1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class Java1Application {

	public static void main(String[] args) {
		SpringApplication.run(Java1Application.class, args);
	}

}
