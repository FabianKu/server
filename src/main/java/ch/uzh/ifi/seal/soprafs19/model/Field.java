package ch.uzh.ifi.seal.soprafs19.model;

import ch.uzh.ifi.seal.soprafs19.entity.Game;

import javax.persistence.*;

@Entity
public class Field {

    //CONSTRUCTORS
    public Field(){};
    public Field(int posX, int posY, int heightOfBuilding, Worker worker) {
        this.posX = posX;
        this.posY = posY;
        this.heightOfBuilding = heightOfBuilding;
        this.worker = worker;
    }

    //ATTRIBUTES
    @Id
    @GeneratedValue
    private long fieldId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "gameId")
    private Game game;

    private int posX;
    private int posY;
    private int heightOfBuilding;

    @OneToOne(cascade=CascadeType.ALL)
    @JoinColumn(nullable = false, referencedColumnName = "workerId")
    private Worker worker;

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

    public int getHeightOfBuilding() {
        return heightOfBuilding;
    }

    public void setHeightOfBuilding(int heightOfBuilding) {
        this.heightOfBuilding = heightOfBuilding;
    }

    public Worker getWorker() {
        return worker;
    }

    public void setWorker(Worker worker) {
        this.worker = worker;
    }
}

