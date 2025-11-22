package com.jotanunes.especificacoes;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class Startup {

	public static void main(String[] args) {
		SpringApplication.run(Startup.class, args);
		System.out.println("Aplicação executada com sucesso!");
	}

}
