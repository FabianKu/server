package ch.uzh.ifi.seal.soprafs19.model;

import ch.uzh.ifi.seal.soprafs19.entity.Worker;

public class HighlightM {
    //CONSTRUCTOR
    public HighlightM(){};
    public HighlightM(Worker worker) {
        this.worker = worker;
    }

    //ATTRIBUTES
    private Worker worker;

    //GETTERS AND SETTERS
    public Worker getWorker() {
        return worker;
    }

    public void setWorker(Worker worker) {
        this.worker = worker;
    }
}
