package ch.uzh.ifi.seal.soprafs19.entity;

import ch.uzh.ifi.seal.soprafs19.godCards.NormalPlayer;
import ch.uzh.ifi.seal.soprafs19.model.Worker;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Player implements Serializable {
    //CONSTRUCTOR
    public Player(){};
    public Player(long userI, int availableMoves, int availableBuilds) {
        this.userId = userId;
        this.availableMoves = availableMoves;
        this.availableBuilds = availableBuilds;
        List<Worker> playersWorkers= new ArrayList<Worker>();
    }

    //ATTRIBUTES
    @Id
    @GeneratedValue
    private Long playerId;

    @Column
    private long userId;

    @OneToMany(mappedBy = "player", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Worker> playersWorkers;

    @Column(nullable = false)
    private int availableMoves;

    @Column
    private int availableBuilds;

    @OneToOne(cascade=CascadeType.ALL)
    @JoinColumn(nullable = false, referencedColumnName = "godCardId")
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

    public List<Worker> getPlayersWorkers() {
        return playersWorkers;
    }

    public void setPlayersWorkers(Worker worker) {
        this.playersWorkers.add(worker);
    }

    public int getAvailableMoves() {
        return availableMoves;
    }

    public void setAvailableMoves(int availableMoves) {
        this.availableMoves = availableMoves;
    }

    public int getAvailableBuilds() {
        return availableBuilds;
    }

    public void setAvailableBuilds(int availableBuilds) {
        this.availableBuilds = availableBuilds;
    }

    public NormalPlayer getGodCard(){return godCard;}

    public void setGodCard(NormalPlayer godCard){this.godCard=godCard;}
}