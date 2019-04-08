package ch.uzh.ifi.seal.soprafs19.model;

import ch.uzh.ifi.seal.soprafs19.entity.Game;

import javax.persistence.*;

@Entity
public class Action {
    //CONSTRUCTOR
    public Action(){};
    public Action(Move move, Build build, int numberMoves) {
        this.move = move;
        this.build = build;
        this.numberMoves = numberMoves;
    }

    //ATTRIBUTES
    private int numberMoves;

    @OneToOne(cascade=CascadeType.ALL)
    @JoinColumn(nullable = false, referencedColumnName = "moveId")
    private Move move;

    @OneToOne(cascade=CascadeType.ALL)
    @JoinColumn(nullable = false, referencedColumnName = "buildId")
    private Build build;

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "gameId")
    private Game game;

    //GETTERS AND SETTERS
    public Move getMove() {
        return move;
    }

    public void setMove(Move move) {
        this.move = move;
    }

    public Build getBuild() {
        return build;
    }

    public void setBuild(Build build) {
        this.build = build;
    }

    public int getNumberMoves() {
        return numberMoves;
    }

    public void setNumberMoves(int numberMoves) {
        this.numberMoves = numberMoves;
    }
}
