package com.buccodev.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class TournamentItatibaOpenAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(TournamentItatibaOpenAppApplication.class, args);
	}
}
