package ch.uzh.ifi.seal.soprafs19.entity;

import javax.persistence.*;

@Entity
public class Worker {
    //CONSTRUCTOR
    public Worker(){};
    public Worker(long playerId){
        this.playerId=playerId;
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

    @Column
    private long playerId;

    @Override
    public boolean equals(Object o) {
        if (o == this) return true;
        if (!(o instanceof Worker)) {
            return false;
        }
        Worker worker = (Worker) o;
        return (this.getWorkerId()==(worker.getWorkerId()));
    }
    //GETTERS AND SETTERS
    public long getPlayerId() {
        return playerId;
    }

    public void setPlayerId(long playerId) {
        this.playerId = playerId;
    }

    public Field getField() {
        return field;
    }

    public void setField(Field field) {
        this.field = field;
    }

    public long getWorkerId() {
        return workerId;
    }

    public void setWorkerId(long workerId) {
        this.workerId = workerId;
    }
}
