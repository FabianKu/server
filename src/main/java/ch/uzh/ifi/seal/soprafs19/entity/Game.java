package ch.uzh.ifi.seal.soprafs19.entity;

import ch.uzh.ifi.seal.soprafs19.model.Action;
import ch.uzh.ifi.seal.soprafs19.model.Field;
import ch.uzh.ifi.seal.soprafs19.model.Turn;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Game implements Serializable {
    //CONSTRUCTOR
    public Game(){};
    public Game(Player player1, Player player2, List<Field> board, int isTerminated, Turn turn) {
        this.player1 = player1;
        this.player2 = player2;
        this.board = board;
        this.isTerminated = isTerminated;
        this.turn=turn;
        List<Action> history=new ArrayList<Action>();
    }

    //ATTRIBUTES
    @Id
    @GeneratedValue
    private Long gameId;

    @Column
    private Player player1;

    @Column
    private Player player2;

    @OneToMany(mappedBy = "game", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Field> board;

    @Column
    private int isTerminated;

    @OneToOne(cascade=CascadeType.ALL)
    @JoinColumn(nullable = false, referencedColumnName = "id")
    private Turn turn;

    @OneToMany(mappedBy = "game", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Action> history;

    //GETTERS AND SETTERS
    public Long getGameId() {
        return gameId;
    }

    public void setGameId(Long gameId) {
        this.gameId = gameId;
    }

    public Player getPlayer1() {
        return player1;
    }

    public void setPlayer1(Player player1) {
        this.player1 = player1;
    }

    public Player getPlayer2() {
        return player2;
    }

    public void setPlayer2(Player player2) {
        this.player2 = player2;
    }

    public List<Field> getBoard() {
        return board;
    }

    public void setBoard(List<Field> board) {
        this.board = board;
    }

    public int getIsTerminated() {
        return isTerminated;
    }

    public void setIsTerminated(int isTerminated) {
        this.isTerminated = isTerminated;
    }

    public Turn getTurnId() {
        return turn;
    }

    public void setTurn(Turn turn) {
        this.turn = turn;
    }

    public List<Action> getHistory() {
        return history;
    }

    public void addToHistory(Action action) {
        history.add(action);
    }
}