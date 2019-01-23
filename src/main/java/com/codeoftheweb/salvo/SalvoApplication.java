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
    public CommandLineRunner initData(PlayerRepository PlayerRepo, GameRepository GameRepo, GamePlayerRepository GamePlayerRepo, ShipRepository shipRepo, SalvoRepository salvoRepo, ScoreRepository scoreRepo) {
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
            Game g5 = new Game();
            Game g6 = new Game();
            Game g7 = new Game();
            Game g8 = new Game();
            GameRepo.save(g1);
            GameRepo.save(g2);
            GameRepo.save(g3);
            GameRepo.save(g4);
            GameRepo.save(g5);
            GameRepo.save(g6);
            GameRepo.save(g7);
            GameRepo.save(g8);


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
            ArrayList<String> destroyer1 = new ArrayList<String>();
            destroyer1.add("H1");
            destroyer1.add("H2");
            destroyer1.add("H3");

            ArrayList<String> destroyer2 = new ArrayList<String>();
            destroyer2.add("B2");
            destroyer2.add("B3");
            destroyer2.add("B4");

            ArrayList<String> submarine1 = new ArrayList<String>();
            submarine1.add("G1");
            submarine1.add("G2");
            submarine1.add("G3");

            ArrayList<String> submarine2 = new ArrayList<String>();
            submarine2.add("G1");
            submarine2.add("G2");
            submarine2.add("G3");

            ArrayList<String> submarine3 = new ArrayList<String>();
            submarine3.add("G1");
            submarine3.add("G2");
            submarine3.add("G3");

            ArrayList<String> patrol1 = new ArrayList<String>();
            patrol1.add("A1");
            patrol1.add("A2");

            ArrayList<String> patrol2 = new ArrayList<String>();
            patrol2.add("A1");
            patrol2.add("A2");

            ArrayList<String> patrol3 = new ArrayList<String>();
            patrol3.add("A1");
            patrol3.add("A2");

            ArrayList<String> carrier1 = new ArrayList<String>();
            carrier1.add("D4");
            carrier1.add("E4");
            carrier1.add("F4");
            carrier1.add("G4");
            carrier1.add("H4");

            ArrayList<String> carrier2 = new ArrayList<String>();
            carrier2.add("F2");
            carrier2.add("F3");
            carrier2.add("F4");
            carrier2.add("F5");
            carrier2.add("F6");

            ArrayList<String> carrier3 = new ArrayList<String>();
            carrier3.add("H4");
            carrier3.add("H5");
            carrier3.add("H6");
            carrier3.add("H7");
            carrier3.add("H8");


            Ship des1 = new Ship("destroyer", destroyer1, gp1);
            Ship des2 = new Ship ("destroyer", destroyer2, gp2);
            Ship sub1 = new Ship("submarine", submarine1, gp1);
            Ship sub2 = new Ship("submarine", submarine2, gp7);
            Ship sub3 = new Ship("submarine", submarine3, gp5);
            Ship pat1 = new Ship("patrolBoat", patrol1, gp1);
            Ship pat2 = new Ship("patrolBoat", patrol2, gp3);
            Ship pat3 = new Ship("patrolBoat", patrol3, gp6);
            Ship car1 = new Ship ("carrier", carrier1, gp2);
            Ship car2 = new Ship ("carrier", carrier2, gp7);
            Ship car3 = new Ship ("carrier", carrier3, gp4);
            shipRepo.save(des1);
            shipRepo.save(des2);
            shipRepo.save(sub1);
            shipRepo.save(sub2);
            shipRepo.save(sub3);
            shipRepo.save(pat1);
            shipRepo.save(pat2);
            shipRepo.save(pat3);
            shipRepo.save(car1);
            shipRepo.save(car2);
            shipRepo.save(car3);


            ArrayList<String> salvoP1 = new ArrayList<String>();
            salvoP1.add("F3");
            salvoP1.add("A8");

            ArrayList<String> salvoP2 = new ArrayList<String>();
            salvoP2.add("G5");
            salvoP2.add("B9");

            ArrayList<String> salvoP3 = new ArrayList<String>();
            salvoP3.add("G3");
            salvoP3.add("F1");

            ArrayList<String> salvoP4 = new ArrayList<String>();
            salvoP4.add("F3");
            salvoP4.add("E1");

            ArrayList<String> salvoP5 = new ArrayList<String>();
            salvoP5.add("G3");



            Salvo salvo1 = new Salvo(gp1, 1, salvoP1);
            Salvo salvo2 = new Salvo(gp2, 1, salvoP2);
            Salvo salvo3 = new Salvo(gp1, 2, salvoP3);
            Salvo salvo4 = new Salvo(gp2, 2, salvoP4);
            Salvo salvo5 = new Salvo(gp2, 2, salvoP5);


            salvoRepo.save(salvo1);
            salvoRepo.save(salvo2);
            salvoRepo.save(salvo3);
            salvoRepo.save(salvo4);
            salvoRepo.save(salvo5);



            scoreRepo.save(new Score(g1, p1,1.0 ));
            scoreRepo.save(new Score(g1, p2, 0.0 ));
            scoreRepo.save(new Score(g2, p1,0.5 ));
            scoreRepo.save(new Score(g2, p2, 0.5 ));
            scoreRepo.save(new Score(g3, p2,1.0));
            scoreRepo.save(new Score(g3, p3,0.0));
            scoreRepo.save(new Score(g4, p2,0.5));
            scoreRepo.save(new Score(g4, p1,0.5));


        };

    }


}

