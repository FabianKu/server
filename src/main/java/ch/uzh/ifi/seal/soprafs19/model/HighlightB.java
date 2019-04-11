package ch.uzh.ifi.seal.soprafs19.model;

import ch.uzh.ifi.seal.soprafs19.entity.Player;
import ch.uzh.ifi.seal.soprafs19.entity.Worker;

public class HighlightB {
    //CONSTRUCTOR
    public HighlightB(){};
    public HighlightB(Player player, Worker worker) {
        this.player = player;
        this.worker = worker;
    }

    //ATTRIBUTES
    private Player player;
    private Worker worker;

    //GETTERS AND SETTERS
    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Worker getWorker() {
        return worker;
    }

    public void setWorker(Worker worker) {
        this.worker = worker;
    }
}
