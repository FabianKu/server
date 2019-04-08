package ch.uzh.ifi.seal.soprafs19.model;

import ch.uzh.ifi.seal.soprafs19.entity.Player;

import javax.persistence.*;

@Entity
public class Worker {
    //CONSTRUCTOR
    public Worker(){};
    public Worker(int posX, int posY){
        this.posX=posX;
        this.posY=posY;
    }

    //ATTRIBUTES
    @Id
    @GeneratedValue
    private long workerId;

    @OneToOne(mappedBy = "worker")
    private Build build;

    @OneToOne(mappedBy = "worker")
    private Move move;

    @OneToOne(mappedBy = "worker")
    private Field field;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "playerId")
    private Player player;

    private int posX;
    private int posY;

    //GETTERS AND SETTERS
    public int getPosX() {
        return posX;
    }

    public void setPosX(int posX) {
        this.posX = posX;
    }

    public int getPosY() {
        return posY;
    }

    public void setPosY(int posY) {
        this.posY = posY;
    }
}
