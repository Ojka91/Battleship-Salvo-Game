package com.codeoftheweb.salvo;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.net.Authenticator;
import java.util.*;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("/api")
public class SalvoController {

    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private GamePlayerRepository gamePlayerRepository;

    @Autowired
    private PlayerRepository playerRepository;

    @RequestMapping("/games")
    public Map<String, Object> getGames(Authentication authentication) {
        Map<String, Object> dto = new LinkedHashMap<String, Object>();
        if(isGuest(authentication)){
            dto.put("player", null);
        }else{
        dto.put("username", playersDTO(isAuth(authentication)));

        }
        dto.put("game", gameRepository.findAll()
                .stream()
                .map(element -> gameDTO(element))
                .collect(toList()));

        return dto;

    }


    @RequestMapping("/leaderboard")
    public Map<String, Object> getScores(GamePlayer gamePlayer) {
          Map<String, Object> dto = new LinkedHashMap<String, Object>();
          List<GamePlayer> gamePlayers = gamePlayerRepository.findAll();
        for (GamePlayer gp: gamePlayers) {
            Map<String, Object> scores = new LinkedHashMap<>();
            if (!scores.containsKey(gp.getPlayer().getPlayerUsername())){
                scores.put("Wins", gp.getPlayer().getScore().stream().filter(sc -> sc.getPoints() == 1).count());
                scores.put("Losses", gp.getPlayer().getScore().stream().filter(sc -> sc.getPoints() == 0).count());
                scores.put("Draw", gp.getPlayer().getScore().stream().filter(sc -> sc.getPoints() == 0.5).count());
                scores.put("Total", gp.getPlayer().getScore().stream().mapToDouble(score -> score.getPoints()).sum());
                dto.put(gp.getPlayer().getPlayerUsername(), scores);
            }
        }

        return dto;

    }




    private Map<String, Object> gameDTO(Game game) {
        Map<String, Object> dto = new LinkedHashMap<String, Object>();
        dto.put("id", game.getId());
        dto.put("created", game.getDate());
        dto.put("GamePlayers", game.getGamePlayers()
                .stream()
                .map(gamePlayer -> gamePlayerDTO(gamePlayer))
                .collect(toList()));
        return dto;
    }

    private Map<String, Object> playersDTO(Player player) {
        Map<String, Object> dto = new LinkedHashMap<String, Object>();
        dto.put("id", player.getId());
        dto.put("email", player.getPlayerEmail());
        dto.put("username", player.getPlayerUsername());
        return dto;
    }

    private Map<String, Object> gamePlayerDTO(GamePlayer gamePlayer) {
        Map<String, Object> dto = new LinkedHashMap<String, Object>();
        dto.put("id", gamePlayer.getId());
        dto.put("player", playersDTO(gamePlayer.getPlayer()));
        if(gamePlayer.getScore() != null) {
            dto.put("score", gamePlayer.getScore().getPoints());

        }else{
            dto.put("score", null);
        }
        return dto;
    }

    private Map<String, Object> shipDTO(Ship ship) {
        Map<String, Object> dto = new LinkedHashMap<String, Object>();
        dto.put("type", ship.getShipType());
        dto.put("position", ship.getShipPosition());

        return dto;
    }

    private Map<String, Object> salvoDTO(Salvo salvo) {
        Map<String, Object> dto = new LinkedHashMap<String, Object>();
        dto.put("turn", salvo.getTurn());
        dto.put("gp", salvo.getGamePlayerSalvo().getPlayer().getId());
        dto.put("position", salvo.getSalvoPosition());


        return dto;
    }

    // -------COMMON METHODS -------//
    //method that look for the opponent GP
    private GamePlayer getOpponent(GamePlayer gamePlayer) {
        return gamePlayer.getGame().getGamePlayers()
                .stream()
                .filter(gamePlayer1 -> gamePlayer1.getId() != gamePlayer.getId()).findAny().orElse(null);
    }


    //method that looks for current user
    private Player isAuth (Authentication authentication){

            return playerRepository.findByPlayerEmail(authentication.getName());

    }
    private Map<String, Object> makeMap(String key, Object value) {
        Map<String, Object> map = new HashMap<>();
        map.put(key, value);
        return map;
    }

    private boolean isGuest(Authentication authentication) {
        return authentication == null || authentication instanceof AnonymousAuthenticationToken;
    }

    // END -------COMMON METHODS -------//

    @RequestMapping("/game_view/{gameId}")
    public Map<String, Object> getGameView(@PathVariable Long gameId) {
        GamePlayer gamePlayer = gamePlayerRepository.getOne(gameId);

        Map<String, Object> dto = new LinkedHashMap<String, Object>();
        dto.put("id", gamePlayer.getGame().getId());
        dto.put("created", gamePlayer.getGame().getDate());
        dto.put("gameplayers", gamePlayer.getGame().getGamePlayers().stream()
                .map(gp -> gamePlayerDTO(gp))
                .collect(toList()));
        dto.put("shipsOwner", gamePlayer.getShips()
                .stream()
                .map(sh -> shipDTO(sh))
                .collect(toList()));
        dto.put("salvoesOwner", gamePlayer.getSalvos()
                .stream()
                .map(sa -> salvoDTO(sa))
                .collect(toList()));
        dto.put("salvoesEnemy", getOpponent(gamePlayer).getSalvos()
                .stream()
                .map(en -> salvoDTO(en))
                .collect(toList()));

        return dto;
    }
    @Autowired
    private PasswordEncoder passwordEncoder;

    @RequestMapping(path = "/players", method = RequestMethod.POST)
    public ResponseEntity<Object> register(

           @RequestBody Player player) {

        if ( player.getPlayerEmail().isEmpty() || player.getPassword().isEmpty()) {
            return new ResponseEntity<>("Missing data", HttpStatus.FORBIDDEN);
        }

        if (playerRepository.findByPlayerEmail(player.getPlayerEmail()) !=  null) {
            return new ResponseEntity<>("Name already in use", HttpStatus.FORBIDDEN);
        }

        playerRepository.save(new Player(player.getPlayerEmail(), player.getPassword()));
        return new ResponseEntity<>(makeMap("player", player.getPlayerEmail()), HttpStatus.CREATED);

    }




}

