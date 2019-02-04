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
    private ShipRepository shipRepository;

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
                dto.put(gp.getPlayer().getPlayerEmail(), scores);
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

    @RequestMapping("/game_view/{gpId}")
    public Map<String, Object> getGameView(@PathVariable Long gpId, Authentication authentication) {
           List<Long> gps = isAuth(authentication).getGamePlayers()
                   .stream()
                   .map(gp->gp.getId())
                   .collect(toList());

        if (gps.contains(gpId)){
            GamePlayer gamePlayer = gamePlayerRepository.getOne(gpId);

            Map<String, Object> dto = new LinkedHashMap<String, Object>();
            dto.put("id", gamePlayer.getGame().getId());
            dto.put("created", gamePlayer.getGame().getDate());
            dto.put("gameplayers", gamePlayer.getGame().getGamePlayers().stream()
                    .map(gp -> gamePlayerDTO(gp))
                    .collect(toList()));
            if (gamePlayer.getShips().size() > 0){
            dto.put("shipsOwner", gamePlayer.getShips()
                    .stream()
                    .map(sh -> shipDTO(sh))
                    .collect(toList()));
            }
            else{
                dto.put("shipsOwner", null);
            }
            if (gamePlayer.getSalvos().size() > 0){
                dto.put("salvoesOwner", gamePlayer.getSalvos()
                        .stream()
                        .map(sa -> salvoDTO(sa))
                        .collect(toList()));
            }
            else{
                dto.put("salvoesOwner", null);
            }
          if(getOpponent(gamePlayer) != null){  dto.put("salvoesEnemy", getOpponent(gamePlayer)
                    .getSalvos()
                    .stream()
                    .map(en -> salvoDTO(en))
                    .collect(toList()));
          }
          else{
              dto.put("salvoesEnemy", null);
          }

            return dto;
        }else{
            return makeMap("error", HttpStatus.UNAUTHORIZED);
        }
    }
    @Autowired
    private PasswordEncoder passwordEncoder;

    @RequestMapping(path = "/players", method = RequestMethod.POST)
    public ResponseEntity<Object> register(

           @RequestBody Player player) {

        if ( player.getPlayerEmail().isEmpty() || player.getPassword().isEmpty()) {
            return new ResponseEntity<>(makeMap("error","Missing data" ), HttpStatus.FORBIDDEN);
        }

        if (playerRepository.findByPlayerEmail(player.getPlayerEmail()) !=  null) {
            return new ResponseEntity<>(makeMap("error","Name already in use"), HttpStatus.FORBIDDEN);
        }

        playerRepository.save(new Player(player.getPlayerEmail(), player.getPassword()));
        return new ResponseEntity<>(makeMap("player", player.getPlayerEmail()), HttpStatus.CREATED);

    }





    @RequestMapping(path = "/games", method = RequestMethod.POST)
    public ResponseEntity<Object> createGame(Authentication authentication){


        if (authentication == null) {
            return new ResponseEntity<>(makeMap("error","not logged in"), HttpStatus.UNAUTHORIZED);
        }
        else{
            Game game = new Game();
            gameRepository.save(game);
            GamePlayer gp = new GamePlayer(game, isAuth(authentication));
            gamePlayerRepository.save(gp);
            return new ResponseEntity<>(makeMap("gpId",gp.getId()), HttpStatus.CREATED);
        }


    }

    @RequestMapping(path = "/game/{gId}/players", method = RequestMethod.POST)
    public ResponseEntity<Object> joinGame(@PathVariable Long gId, Authentication authentication){

        if(isAuth(authentication) !=null){
            if(gameRepository.getOne(gId) != null){
                if(gameRepository.getOne(gId).getGamePlayers().size() < 2){
                    GamePlayer gp = new GamePlayer(gameRepository.getOne(gId), isAuth(authentication));
                    gamePlayerRepository.save(gp);
                    return new ResponseEntity<>(makeMap("gpId",gp.getId()), HttpStatus.CREATED);
                }
                else{
                    return new ResponseEntity<>(makeMap("error", "game full"), HttpStatus.FORBIDDEN);
                }
            }
            else{
                return new ResponseEntity<>(makeMap("error", "game doesn't exist"), HttpStatus.FORBIDDEN);
            }
        }
        else{
            return new ResponseEntity<>(makeMap("error", "none is logged in"), HttpStatus.UNAUTHORIZED);
        }


    }



    @RequestMapping(path = "/games/players/{gamePlayerId}/ships", method = RequestMethod.POST)
    public ResponseEntity<Object> createShips(@PathVariable Long gamePlayerId, Authentication authentication, @RequestBody List<Ship> ships) {

        if(isAuth(authentication) !=null){
            if(gamePlayerRepository.getOne(gamePlayerId) != null){
                if(gamePlayerRepository.getOne(gamePlayerId) != null){
                    if(gamePlayerRepository.getOne(gamePlayerId).getShips() != null){
                        GamePlayer currentGp = gamePlayerRepository.getOne(gamePlayerId);

                        for (Ship ship: ships) {
                            ship.setGamePlayers(currentGp);
                            shipRepository.save(ship);
                        }
                        return  new ResponseEntity<>(makeMap("succes", "ships placed"), HttpStatus.CREATED);
                    }
                    else{
                        return new ResponseEntity<>(makeMap("error", "already ships placed"), HttpStatus.FORBIDDEN);
                    }

                }
                else{
                    return new ResponseEntity<>(makeMap("error", "this is not your game"), HttpStatus.UNAUTHORIZED);
                }
            }
            else{
                return new ResponseEntity<>(makeMap("error", "no gp with given ID"), HttpStatus.UNAUTHORIZED);
            }
        }
        else{
            return new ResponseEntity<>(makeMap("error", "none is logged in"), HttpStatus.UNAUTHORIZED);
        }
    }

}

