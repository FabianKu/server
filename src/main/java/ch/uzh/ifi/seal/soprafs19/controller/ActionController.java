package ch.uzh.ifi.seal.soprafs19.controller;

import ch.uzh.ifi.seal.soprafs19.entity.Game;
import ch.uzh.ifi.seal.soprafs19.model.*;
import ch.uzh.ifi.seal.soprafs19.service.GameService;
import ch.uzh.ifi.seal.soprafs19.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;


@RestController
public class ActionController {

    //LOGGER
    private final Logger log = LoggerFactory.getLogger(UserService.class);

    //CONSTANTS
    private final UserService service;
    private final GameService gameservice;

    //CONSTRUCTOR
    ActionController(UserService service, GameService gameservice) {
        this.service = service;
        this.gameservice = gameservice;
    }

    //USEFUL FUNCTIONS
    private long StringToLong(String x){
        try {
            long result = Long.parseLong(x);
            return (result);
        }catch (Exception e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Id is not a Long");
        }
    }

    private boolean StringToBoolean(String x){
        try {
            boolean result = Boolean.parseBoolean(x);
            return (result);
        }catch (Exception e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Id is not a Boolean");
        }
    }

    private int StringToInt(String x){
        try {
            int result = Integer.parseInt(x);
            return (result);
        }catch (Exception e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Id is not an Integer");
        }
    }

    //MAPPINGS
    @PostMapping("/movement/{gameId}")
    public Game moveWorker(@RequestHeader(value = "token") String token, @PathVariable long gameId, @RequestBody Move move){
        return(this.gameservice.move(move));
    }

    @PostMapping("/building/{gameId}")
    public Game build(@RequestHeader(value = "token") String token,@PathVariable long gameId, @RequestBody Build build){
        return(this.gameservice.build(build));
    }

    @GetMapping("/turn/{gameId}")
    public Game turn(@RequestHeader(value = "token") String token,@PathVariable long gameId){
        return(this.gameservice.changeTurn(gameId));
    }

    @GetMapping("/highlightBuilding/{gameId}")
    public ArrayList<Field> highlightBuilding(@PathVariable long gameId, @RequestBody HighlightB highlightB){
        return(this.gameservice.highlightB(highlightB, gameId));
    }

    @GetMapping("/highlightMovement/{gameId}")
    public ArrayList<Field> highlightMovement(@PathVariable long gameId, @RequestBody HighlightM highlightM){
        return(this.gameservice.highlightM(highlightM, gameId));
    }



}
