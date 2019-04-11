package ch.uzh.ifi.seal.soprafs19.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


@Entity
public class Game implements Serializable {
    //CONSTRUCTOR
    public Game(){};
    public Game(boolean isTerminated, Turn turn) {
        this.isTerminated = isTerminated;
        this.turn=turn;
        List<Action> history=new ArrayList<Action>();
        this.history=history;
        List<Field> board = new ArrayList<Field>();
        this.board=createBoard(board);
        List<Player> players=new ArrayList<Player>();
        this.players=players;
    }

    //USEFUL FUNCTIONS
    private List<Field> createBoard(List<Field> board){
        for (int i=1;i!=6;i++){
            for(int j=1;j!=6;j++){
                Field field=new Field(j,i,0,null,this);
                board.add(field);
            }
        }
        return(board);
    }

    //ATTRIBUTES
    @Id
    @GeneratedValue
    private Long gameId;

    @OneToMany(mappedBy = "game", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Player> players;

    @OneToMany(mappedBy = "game", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Field> board;

    @Column
    private boolean isTerminated;

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

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(Player player1, Player player2) {
        this.players.add(player1);
        this.players.add(player2);
    }

    public List<Field> getBoard() {
        return this.board;
    }
/*
    public void setBoard(Field field) {
        int posX=field.getPosX();
        int posY=field.getPosY();
        this.board[posX][posY]=field;
    }*/

    public boolean getIsTerminated() {
        return isTerminated;
    }

    public void setIsTerminated(boolean isTerminated) {
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