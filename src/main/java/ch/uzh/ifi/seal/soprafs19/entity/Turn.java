package ch.uzh.ifi.seal.soprafs19.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import java.io.Serializable;

@Entity
public class Turn implements Serializable {
    //CONSTRUCTOR
    public Turn(){};
    public Turn(Player player, int movesLeft, int buildsLeft, boolean hasBuilt, boolean hasMoved, int buildsLeft1, boolean hasBuilt1, boolean hasMoved1) {
        this.player = player;
        this.movesLeft = movesLeft;
        this.buildsLeft = buildsLeft;
        this.hasBuilt = hasBuilt;
        this.hasMoved = hasMoved;
        this.buildsLeft = buildsLeft1;
        this.hasBuilt = hasBuilt1;
        this.hasMoved = hasMoved1;
    }

    //ATTRIBUTES
    @OneToOne(mappedBy = "turn")
    private Game game;

    @Id
    @GeneratedValue
    private Long Id;

    private Player player;
    private int movesLeft;
    private int buildsLeft;
    private boolean hasBuilt;
    private boolean hasMoved;

    //GETTERS AND SETTERS
    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public int getMovesLeft() {
        return movesLeft;
    }

    public void setMovesLeft(int movesLeft) {
        this.movesLeft = movesLeft;
    }

    public int getBuildsLeft() {
        return buildsLeft;
    }

    public void setBuildsLeft(int buildsLeft) {
        this.buildsLeft = buildsLeft;
    }

    public boolean isHasBuilt() {
        return hasBuilt;
    }

    public void setHasBuilt(boolean hasBuilt) {
        this.hasBuilt = hasBuilt;
    }

    public boolean isHasMoved() {
        return hasMoved;
    }

    public void setHasMoved(boolean hasMoved) {
        this.hasMoved = hasMoved;
    }

}
