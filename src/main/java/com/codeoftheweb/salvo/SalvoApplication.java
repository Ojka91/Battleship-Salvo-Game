package com.codeoftheweb.salvo;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;

@SpringBootApplication
public class SalvoApplication {

	public static void main(String[] args) {
		SpringApplication.run(SalvoApplication.class, args);
	}

	@Bean
	public CommandLineRunner initData(PlayerRepository PlayerRepo, GameRepository GameRepo, GamePlayerRepository GamePlayerRepo, ShipRepository shipRepo) {
		return (args) -> {
			// save a couple of customers
			Player p1 = new Player ( "Jack", "example1@gmail.com");
			Player p2 = new Player ("Chloe", "example2@gmail.com");
			Player p3 = new Player ("Kim", "example3@gmail.com");
			Player p4 = new Player ("David", "example4@gmail.com");
			Player p5 = new Player("Michelle", "example5@gmail.com");
			Game g1 = new Game ();
			Game g2 = new Game ();
			Game g3 = new Game ();
			Game g4 = new Game ();
			ArrayList<String> destructor = new ArrayList<String>();
					destructor.add("h1");
					destructor.add("h2");
					destructor.add("h3");

			Ship des = new Ship("Destructor", destructor);
			shipRepo.save(des);

			PlayerRepo.save(p1);
			PlayerRepo.save(p2);
			PlayerRepo.save(p3);
			PlayerRepo.save(p4);
			PlayerRepo.save(p5);

			GameRepo.save(g1);
			GameRepo.save(g2);
			GameRepo.save(g3);
			GameRepo.save(g4);

			GamePlayerRepo.save(new GamePlayer(g1, p1));
			GamePlayerRepo.save(new GamePlayer(g1, p2));
			GamePlayerRepo.save(new GamePlayer(g2, p3));
			GamePlayerRepo.save(new GamePlayer(g2, p4));
			GamePlayerRepo.save(new GamePlayer(g3, p4));
			GamePlayerRepo.save(new GamePlayer(g3, p1));
			GamePlayerRepo.save(new GamePlayer(g4, p3));








		};

	}


}

