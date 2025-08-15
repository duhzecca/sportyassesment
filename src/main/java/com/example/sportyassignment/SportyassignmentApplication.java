package com.example.sportyassignment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class SportyassignmentApplication {

	public static void main(String[] args) {
		SpringApplication.run(SportyassignmentApplication.class, args);
	}

}
