package com.codeoftheweb.salvo;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SalvoApplication {

	public static void main(String[] args) {
		SpringApplication.run(SalvoApplication.class, args);
	}
	@Bean
	public CommandLineRunner initData(PlayerRepository repository) {
		return (args) -> {
			// save a couple of customers
			repository.save(new Player("Jack", "example1@gmail.com"));
			repository.save(new Player("Chloe", "example2@gmail.com"));
			repository.save(new Player("Kim", "example3@gmail.com"));
			repository.save(new Player("David", "example4@gmail.com"));
			repository.save(new Player("Michelle", "example5@gmail.com"));

		};
	}

}

