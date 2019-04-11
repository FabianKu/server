package ch.uzh.ifi.seal.soprafs19.entity;

import ch.uzh.ifi.seal.soprafs19.entity.godCards.NormalPlayer;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class Player implements Serializable {
    //CONSTRUCTOR
    public Player(){};
    public Player(long userId) {
        this.userId = userId;
        //Initiate the correct godCard
        //how to find the right card?
        this.godCard= new NormalPlayer();

    }

    //ATTRIBUTES
    @Id
    @GeneratedValue
    private Long playerId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "gameId")
    private Game game;

    @Column
    private long userId;

    @OneToOne(cascade=CascadeType.ALL)
    @JoinColumn(nullable = false,referencedColumnName = "godCardId")
    private NormalPlayer godCard;

    //GETTERS AND SETTERS
    public Long getPlayerId() {
        return playerId;
    }

    public void setPlayerId(Long playerId) {
        this.playerId = playerId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public NormalPlayer getGodCard(){return godCard;}

    public void setGodCard(NormalPlayer godCard){this.godCard=godCard;}

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }
}