package ch.uzh.ifi.seal.soprafs19.service;


import ch.uzh.ifi.seal.soprafs19.entity.*;
import ch.uzh.ifi.seal.soprafs19.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class GameService {

    //LOGGER
    private final Logger log = LoggerFactory.getLogger(UserService.class);

    //CONSTANTS
    private final ch.uzh.ifi.seal.soprafs19.repository.GameRepository GameRepository;
    private final ch.uzh.ifi.seal.soprafs19.repository.UserRepository UserRepository;

    //CONSTRUCTOR
    @Autowired
    public GameService(ch.uzh.ifi.seal.soprafs19.repository.GameRepository GameRepository, ch.uzh.ifi.seal.soprafs19.repository.UserRepository UserRepository) { this.GameRepository = GameRepository; this.UserRepository = UserRepository;
    }

    //USEFUL FUNCTIONS
    private Field findFieldWithWorker(Worker worker, long gameId){
            Game game = this.GameRepository.findById(gameId);
            List<Field> boardOfCurrentGame=game.getBoard();
            //iterate over all fields to find the Field with the worker
            for (Field currentField : boardOfCurrentGame) {
                if(currentField.equals(worker.getField())){
                    return(currentField);
                }
            }
        throw new ResponseStatusException(HttpStatus.CONFLICT,"No worker with Id " + worker.getWorkerId() + " in game with Id " + gameId + " was found");
    }

    private long convertToLong(Long id){
        long i=id;
        return (i);
    }

    private Field findFieldWithCoordinates(int posX, int posY, long gameId){
        Game game = this.GameRepository.findById(gameId);
        List<Field> boardOfCurrentGame=game.getBoard();
        //iterate over all fields to find the Field with the worker
        for (Field currentField : boardOfCurrentGame) {
            //get Coordinates
            if(currentField.getPosY()==posY && currentField.getPosX()==posX){
                return(currentField);
            }
        }
        throw new ResponseStatusException(HttpStatus.CONFLICT, "No field with posx "+posX+" and posY " +posY+ " was found");
    }

    public void createGame(Game game){
        GameRepository.save(game);
        for(Player player:game.getPlayers()){
            UserRepository.findById(player.getUserId()).setInGame(true);
        }
    }

    public void deleteGame(Game game){
        this.GameRepository.delete(game);

    }

    public Game getGame(Long gameId){
        return(this.GameRepository.findById(convertToLong(gameId)));
    }

    public void update(long gameId, Game newstategame){
        Game oldstateGame=this.GameRepository.findById(gameId);
        //änderungen vornehmen
    }

    public void leaveGame(long userId, long gameId){
        //kick the User out of the game
    }

    public boolean checkIfWorkerOnField(Field field){
        if(field.getWorker()==null){
            return false;
        } else {
            return true;
        }
    }

    public Game move(Move move){
        //Game oldStateGame=this.GameRepository.findById(move.getGameId());

        int posX = move.getPosX();
        int posY = move.getPosY();
        long gameId = move.getGameId();
        Worker worker = move.getWorker();

        Field fieldTo = findFieldWithCoordinates(posX,posY,gameId);

        if(!checkIfWorkerOnField(fieldTo)) {
            fieldTo.setWorker(worker);
            Game newStateGame=this.GameRepository.findById(move.getGameId());
            return newStateGame;
        }
         throw new ResponseStatusException(HttpStatus.CONFLICT);
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
            //get the Worker from the highlightM object
            Worker worker=highlightM.getWorker();
            //get the Field the Worker is on
            Field currentField = findFieldWithWorker(worker, gameId);
            //get position x and y
            int posX=currentField.getPosX();
            int posY=currentField.getPosY();
            //create the return Array
            ArrayList<Field> fieldsToHighlight= new ArrayList<Field>();
            //Iterate through all surrounding fields to find those which aren't occupied by a worker or by a dome and are inside the board
            //and the height of the field is not higher than one + current field
            for(int i=posX-1;i!=posX+2;i++){
                for(int j=posY-1;j!=posY+2;j++){
                    if(i>=1 && i<=5 && j>=1 && j<=5){
                        Field fieldToCheck=findFieldWithCoordinates(i,j,gameId);
                        if(fieldToCheck.getHeightOfBuilding()!=4 && fieldToCheck.getWorker()==null
                        && fieldToCheck.getHeightOfBuilding()<=currentField.getHeightOfBuilding()+1 && currentField!=fieldToCheck){
                            Field field=new Field();
                            field.setPosX(i);
                            field.setPosY(j);
                            fieldsToHighlight.add(field);
                    }
                    }

                }
            }
            return(fieldsToHighlight);
    }
}