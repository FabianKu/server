package ch.uzh.ifi.seal.soprafs19.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class Field implements Serializable {

    //CONSTRUCTORS
    public Field(){};
    public Field(int posX, int posY, int heightOfBuilding, Worker worker, Game game) {
        this.posX = posX;
        this.posY = posY;
        this.heightOfBuilding = heightOfBuilding;
        this.worker = worker;
        if(worker!=null){
            worker.setField(this);
        }
        this.game=game;

    }

    //USEFUL FUNCTIONS
    @Override
    public boolean equals(Object o) {
        if (o == this) return true;
        if (!(o instanceof Field)) {
            return false;
        }
        Field field = (Field) o;
        return (this.getPosX()==field.getPosX()&& this.getPosY()==field.getPosY());
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

    @JsonIgnore
    @OneToOne(cascade=CascadeType.ALL)
    @JoinColumn(referencedColumnName = "workerId")
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
        worker.setField(this);
    }
}

