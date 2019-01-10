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
            //players creation
            Player p1 = new Player("Jack", "example1@gmail.com");
            Player p2 = new Player("Chloe", "example2@gmail.com");
            Player p3 = new Player("Kim", "example3@gmail.com");
            Player p4 = new Player("David", "example4@gmail.com");
            Player p5 = new Player("Michelle", "example5@gmail.com");
            PlayerRepo.save(p1);
            PlayerRepo.save(p2);
            PlayerRepo.save(p3);
            PlayerRepo.save(p4);
            PlayerRepo.save(p5);

            //games creation
            Game g1 = new Game();
            Game g2 = new Game();
            Game g3 = new Game();
            Game g4 = new Game();
            GameRepo.save(g1);
            GameRepo.save(g2);
            GameRepo.save(g3);
            GameRepo.save(g4);


            //gameplayers creation
            GamePlayer gp1 = new GamePlayer(g1, p1);
            GamePlayer gp2 = new GamePlayer(g1, p2);
            GamePlayer gp3 = new GamePlayer(g2, p3);
            GamePlayer gp4 = new GamePlayer(g2, p4);
            GamePlayer gp5 = new GamePlayer(g3, p1);
            GamePlayer gp6 = new GamePlayer(g3, p3);
            GamePlayer gp7 = new GamePlayer(g4, p5);
            GamePlayerRepo.save(gp1);
            GamePlayerRepo.save(gp2);
            GamePlayerRepo.save(gp3);
            GamePlayerRepo.save(gp4);
            GamePlayerRepo.save(gp5);
            GamePlayerRepo.save(gp6);
            GamePlayerRepo.save(gp7);

            //ships creation
            ArrayList<String> destructor = new ArrayList<String>();
            destructor.add("h1");
            destructor.add("h2");
            destructor.add("h3");

            ArrayList<String> submarine = new ArrayList<String>();
            destructor.add("g1");
            destructor.add("g2");
            destructor.add("g3");

            ArrayList<String> patrol = new ArrayList<String>();
            destructor.add("a1");
            destructor.add("a2");


            Ship des1 = new Ship("Destructor", destructor, gp1);
            Ship sub1 = new Ship("Submarine", submarine, gp1);
            Ship patrol1 = new Ship("Patrol Boat", patrol, gp1);
            shipRepo.save(des1);
            shipRepo.save(sub1);
            shipRepo.save(patrol1);


        };

    }


}

