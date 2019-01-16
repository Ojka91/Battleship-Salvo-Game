package com.codeoftheweb.salvo;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Salvo {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private long id;


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "gamePlayer_id")
    private GamePlayer gamePlayerSalvo;

    @ElementCollection
    @Column(name = "salvoPosition")
    private List<String> salvoPosition = new ArrayList<>();

    private Integer turn;



    public Salvo(){}

    public Salvo(GamePlayer gamePlayer, Integer turn, List<String> salvoPosition){
        this.gamePlayerSalvo = gamePlayer;
        this.turn = turn;
        this.salvoPosition = salvoPosition;
        //gamePlayer.setSalvos(this);
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public GamePlayer getGamePlayerSalvo() {
        return gamePlayerSalvo;
    }

    public void setGamePlayerSalvo(GamePlayer gamePlayerSalvo) {
        this.gamePlayerSalvo = gamePlayerSalvo;
    }

    public List<String> getSalvoPosition() {
        return salvoPosition;
    }

    public void setSalvoPosition(List<String> salvoPosition) {
        this.salvoPosition = salvoPosition;
    }

    public Integer getTurn() {
        return turn;
    }

    public void setTurn(Integer turn) {
        this.turn = turn;
    }
}
