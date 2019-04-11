package ch.uzh.ifi.seal.soprafs19.entity;

import javax.persistence.*;

@Entity
public class Move {

    //CONSTRUCTOR
    public Move(){};
    public Move(int posX, int posY, Worker worker, long gameId) {
        this.posX = posX;
        this.posY = posY;
        this.worker = worker;
        this.gameId = gameId;
    }

    //ATTRIBUTES
    @Id
    @GeneratedValue
    private long moveId;

    @OneToOne(mappedBy = "move")
    private Action action;

    private int posX;
    private int posY;

    @OneToOne(cascade= CascadeType.ALL)
    @JoinColumn(nullable = false, referencedColumnName = "workerId")
    private Worker worker;

    private long gameId;

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

    public Worker getWorker() {
        return worker;
    }

    public void setWorker(Worker worker) {
        this.worker = worker;
    }

    public long getGameId() {
        return gameId;
    }

    public void setGameId(long gameId) {
        this.gameId = gameId;
    }
}
