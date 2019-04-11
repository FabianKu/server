package ch.uzh.ifi.seal.soprafs19.entity.godCards;

import ch.uzh.ifi.seal.soprafs19.entity.Field;
import ch.uzh.ifi.seal.soprafs19.entity.Player;
import ch.uzh.ifi.seal.soprafs19.entity.Worker;

import javax.persistence.*;
import java.util.ArrayList;

@Entity
public class NormalPlayer {
    //CONSTRUCTOR
    public NormalPlayer(){
        //how to find the correct card?
        //defined available builds and moves
        this.availableBuilds=1;
        this.availableMoves=1;
    }
    @Id
    @GeneratedValue
    private long godCardId;

    @Column
    private int typeGodcard;

    @OneToOne(mappedBy = "godCard")
    private Player player;

    @Column
    private int availableMoves;

    @Column
    private int availableBuilds;

    //GETTERS AND SETTERS
    public long getGodCardId() {
        return godCardId;
    }

    public void setGodCardId(long godCardId) {
        this.godCardId = godCardId;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
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

    //FUNCTIONS
    protected ArrayList<Field> getLegalMoves(Worker worker){
        //implement something
        return(new ArrayList<Field>());
    }

    protected ArrayList<Field> getLegalBuilds(Worker worker){
        //implement something
        return(new ArrayList<Field>());
    }

    protected boolean checkWin(Player player){
        //implement something
        return(false);
    }

}
