package ch.uzh.ifi.seal.soprafs19.service;


import ch.uzh.ifi.seal.soprafs19.entity.Game;
import ch.uzh.ifi.seal.soprafs19.entity.Player;
import ch.uzh.ifi.seal.soprafs19.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

@Service
@Transactional
public class GameService {

    private final ch.uzh.ifi.seal.soprafs19.repository.GameRepository GameRepository;

    private long convert_to_long(Long id){
        long i=id;
        return (i);
    }

    @Autowired
    public GameService(ch.uzh.ifi.seal.soprafs19.repository.GameRepository GameRepository) { this.GameRepository = GameRepository;
    }

    public void createGame(){
        Turn turn=new Turn();
        Player player1=new Player();
        Player player2=new Player();
        ArrayList board=new ArrayList();
        Game game= new Game(player1,player2,board,1,turn);
        GameRepository.save(game);
    }

    public Game getGame(Long gameId){
        return(this.GameRepository.findById(convert_to_long(gameId)));
    }

    public void update(long gameId, Game newstategame){
        Game oldstateGame=this.GameRepository.findById(gameId);
        //änderungen vornehmen
    }

    public void leaveGame(long userId, long gameId){
        //kick the User out of the game
    }

    public Game move(Move move){
        Game oldstateGame=this.GameRepository.findById(move.getGameId());

        //update position

        Game newstateGame=this.GameRepository.findById(move.getGameId());
        return(newstateGame);
    }

    public Game build (Build build){
        Game currentGame=this.GameRepository.findById(build.getGameId());
        //build
        Game newstateGame=this.GameRepository.findById(build.getGameId());
        return(newstateGame);
    }


    public void waiveGodCard(long gameId, long userId){
        //waive God Cards
    }

    public Game changeTurn(long gameId){
        Game oldstateGame=this.GameRepository.findById(gameId);
        //change the turn
        Game newstateGame=this.GameRepository.findById(gameId);
        return(newstateGame);
    }

    public ArrayList<Field> highlightB(HighlightB highlightB, long gameId){
        ArrayList<Field> fieldsToHighlight= new ArrayList<Field>();
        //insert all fields into the ArrayList which should be hightlighted
        return(fieldsToHighlight);
    }

    public ArrayList<Field> highlightM(HighlightM highlightM, long gameId){
        ArrayList<Field> fieldsToHighlight= new ArrayList<Field>();
        //insert all fields into the ArrayList which should be hightlighted
        return(fieldsToHighlight);
    }

}