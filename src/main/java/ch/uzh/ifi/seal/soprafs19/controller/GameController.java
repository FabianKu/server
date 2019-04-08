package ch.uzh.ifi.seal.soprafs19.controller;

import ch.uzh.ifi.seal.soprafs19.entity.Game;
import ch.uzh.ifi.seal.soprafs19.model.AllId;
import ch.uzh.ifi.seal.soprafs19.service.GameService;
import ch.uzh.ifi.seal.soprafs19.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;


@RestController
public class GameController {

    //LOGGER
    private final Logger log = LoggerFactory.getLogger(UserService.class);

    //Constants
    private final UserService service;
    private final GameService gameservice;

    //Constructor
    GameController(UserService service, GameService gameservice) {
        this.service = service;
        this.gameservice = gameservice;
    }

    //USEFUL FUNCTIONS
    //converts a string into a long
    //mostly used for Id
    private long convertToLong(String x){
        try {
            long result = Long.parseLong(x);
            return (result);
        }catch (Exception e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Id is not a Long");
        }
    }

    private boolean convertToBoolean(String x){
        try {
            boolean result = Boolean.parseBoolean(x);
            return (result);
        }catch (Exception e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Id is not a Boolean");
        }
    }

    private int convertToInt(String x){
        try {
            int result = Integer.parseInt(x);
            return (result);
        }catch (Exception e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Id is not an Integer");
        }
    }

    //MAPPINGS
    @GetMapping("/game/{gameid}")
    public Game sendsgameback(@PathVariable Long gameid){
        return(this.gameservice.getGame(gameid));
    }

    @PutMapping("/game/{gameid}")
    public void updateGame(@RequestHeader(value = "token") String token,@PathVariable long gameId, @RequestBody Game game){
        this.gameservice.update(gameId,game);
    }

    @PostMapping("/quit/{gameid}")
    public void quitgame(@RequestHeader(value = "token") String token,@PathVariable long gameId, @RequestParam  long userId){
        this.gameservice.leaveGame(userId, gameId);
    }

    @PutMapping("/godcard/{gameId}")
    public void selectGodPower(@RequestHeader(value = "token") String token,@PathVariable long gameId, @RequestBody AllId allId ){
        this.service.updateGodCard(allId);
     }

    @PostMapping("/waive/{gameId")
    public void cancelGodPower(@RequestHeader(value = "token") String token,@PathVariable long gameId, @RequestParam long userId){
        this.gameservice.waiveGodCard(gameId,userId);
    }


    //highlightMove highlightBuild?? zweimal move und build..
}