package com.codeoftheweb.salvo;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.GlobalAuthenticationConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class SalvoApplication  extends SpringBootServletInitializer {

    @Bean
    public PasswordEncoder passwordEncoder() {

        return PasswordEncoderFactories.createDelegatingPasswordEncoder();


    }



    public static void main(String[] args) {
        SpringApplication.run(SalvoApplication.class, args);
    }

    @Bean
    public CommandLineRunner initData(PlayerRepository PlayerRepo, GameRepository GameRepo, GamePlayerRepository GamePlayerRepo, ShipRepository shipRepo, SalvoRepository salvoRepo, ScoreRepository scoreRepo) {
        return (args) -> {


            //players creation
            Player p1 = new Player("Jack", "j.bauer@ctu.gov", "24");
            Player p2 = new Player("Chloe", "c.obrian@ctu.gov", "42");
            Player p3 = new Player("Kim", "kim_bauer@gmail.com", "kb");
            Player p4 = new Player("Tony", "t.almeida@ctu.gov", "mole");

            PlayerRepo.save(p1);
            PlayerRepo.save(p2);
            PlayerRepo.save(p3);
            PlayerRepo.save(p4);


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



            //gameplayers creation
            GamePlayer gp1 = new GamePlayer(g1, p1);
            GamePlayer gp2 = new GamePlayer(g1, p2);
            GamePlayer gp3 = new GamePlayer(g2, p2);
            GamePlayer gp4 = new GamePlayer(g2, p4);
            GamePlayer gp5 = new GamePlayer(g3, p2);
            GamePlayer gp6 = new GamePlayer(g3, p1);
            GamePlayer gp7 = new GamePlayer(g4, p3);

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
            Ship sub3 = new Ship("submarine", submarine3, gp5);
            Ship pat1 = new Ship("patrolBoat", patrol1, gp1);
            Ship pat2 = new Ship("patrolBoat", patrol2, gp3);
            Ship pat3 = new Ship("patrolBoat", patrol3, gp6);
            Ship car1 = new Ship ("carrier", carrier1, gp2);
            Ship car3 = new Ship ("carrier", carrier3, gp4);
            shipRepo.save(des1);
            shipRepo.save(des2);
            shipRepo.save(sub1);
            shipRepo.save(sub3);
            shipRepo.save(pat1);
            shipRepo.save(pat2);
            shipRepo.save(pat3);
            shipRepo.save(car1);
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

@Configuration
class WebSecurityConfiguration extends GlobalAuthenticationConfigurerAdapter {
        @Autowired
        PlayerRepository playerRepository;

        @Autowired
        PasswordEncoder passwordEncoder;

        @Override
        public void init(AuthenticationManagerBuilder auth) throws Exception {
            auth.userDetailsService(inputName-> {
                Player player = playerRepository.findByPlayerEmail(inputName);
                if (player != null) {
                    return new User(player.getPlayerEmail(), passwordEncoder.encode(player.getPassword()),
                            AuthorityUtils.createAuthorityList("USER"));

                } else {
                    throw new UsernameNotFoundException("Unknown user: " + inputName);
                }
            });
        }
}
@EnableWebSecurity
@Configuration
class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/rest/**").hasAuthority("ADMIN")
                .antMatchers("/web/games.html").permitAll()
                .antMatchers("/web/game.html").hasAnyAuthority("USER")
                .antMatchers("/api/game_view/**").hasAuthority("USER")
                .antMatchers("/api/games").hasAnyAuthority("USER")
                .antMatchers("/api/leaderboard").permitAll()
                .antMatchers("/api/players").permitAll()
                .antMatchers("/h2-console/**").permitAll()


                .and()
                .formLogin()
                .usernameParameter("playerEmail")
                .passwordParameter("password")
                .loginPage("/api/login");

                http.logout().logoutUrl("/api/logout");


        http.headers().frameOptions().disable();

        // turn off checking for CSRF tokens
        http.csrf().disable();

        // if user is not authenticated, just send an authentication failure response
        http.exceptionHandling().authenticationEntryPoint((req, res, exc) -> res.sendError(HttpServletResponse.SC_UNAUTHORIZED));

        // if login is successful, just clear the flags asking for authentication
        http.formLogin().successHandler((req, res, auth) -> clearAuthenticationAttributes(req));

        // if login fails, just send an authentication failure response
        http.formLogin().failureHandler((req, res, exc) -> res.sendError(HttpServletResponse.SC_UNAUTHORIZED));

        // if logout is successful, just send a success response
        http.logout().logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler());

        }

    private void clearAuthenticationAttributes(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
        }
    }}

