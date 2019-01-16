package com.codeoftheweb.salvo;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Entity
public class Ship {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private long id;
    private String shipType;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "gamePlayer_id")
    private GamePlayer gamePlayerShip;

    @ElementCollection
    @Column(name = "shipPosition")
    private List<String> shipPosition = new ArrayList<>();

    public Ship() {

    }

    public Ship(String shipType, List<String> shipPosition, GamePlayer gamePlayer) {
        this.shipType = shipType;
        this.shipPosition = shipPosition;
        this.gamePlayerShip = gamePlayer;
        gamePlayer.setShips(this);
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getShipType() {
        return shipType;
    }

    public void setShipType(String shipType) {
        this.shipType = shipType;
    }

    public GamePlayer getGamePlayers() {
        return gamePlayerShip;
    }

    public void setGamePlayers(GamePlayer gamePlayers) {
        this.gamePlayerShip = gamePlayers;
    }

    public List<String> getShipPosition() {
        return shipPosition;
    }

    public void setShipPosition(List<String> shipPosition) {
        this.shipPosition = shipPosition;
    }
}
