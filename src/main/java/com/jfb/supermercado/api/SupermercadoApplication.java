package com.jfb.supermercado.api;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SupermercadoApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(SupermercadoApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
	}

}