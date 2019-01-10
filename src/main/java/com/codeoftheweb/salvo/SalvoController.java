package com.codeoftheweb.salvo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("/api")
public class SalvoController {

    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private GamePlayerRepository gamePlayerRepository;

    @RequestMapping("/games")
    public List<Object> getGames() {
        return gameRepository.findAll()
                .stream()
                .map(element -> gameDTO(element))
                .collect(toList());

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
        return dto;
    }

    @RequestMapping("/gamePlayer")
    public List<GamePlayer> getGamePlayers() {
        return gamePlayerRepository.findAll();
    }


    @RequestMapping("/game_view/{gameId}")
    public  Map<String, Object> getGameView(@PathVariable Long gameId){

        GamePlayer gamePlayer = gamePlayerRepository.getOne(gameId);

        Map<String, Object> dto = new LinkedHashMap<String, Object>();
        dto.put("id",gamePlayer.getGame().getId());
        dto.put("created", gamePlayer.getGame().getDate());
        dto.put("gameplayers", gamePlayer.getGame().getGamePlayers().stream()
                .map(gp -> gamePlayerDTO(gp))
                .collect(toList()));
        return dto;
    }



}
