package ch.uzh.ifi.seal.soprafs19.controller;

import ch.uzh.ifi.seal.soprafs19.entity.Build;
import ch.uzh.ifi.seal.soprafs19.entity.Field;
import ch.uzh.ifi.seal.soprafs19.entity.Game;
import ch.uzh.ifi.seal.soprafs19.entity.Move;
import ch.uzh.ifi.seal.soprafs19.model.AllId;
import ch.uzh.ifi.seal.soprafs19.model.HighlightB;
import ch.uzh.ifi.seal.soprafs19.model.HighlightM;
import ch.uzh.ifi.seal.soprafs19.service.GameService;
import ch.uzh.ifi.seal.soprafs19.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;


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
    private long convertToLong(String x) {
        try {
            long result = Long.parseLong(x);
            return (result);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Id is not a Long");
        }
    }

    private boolean convertToBoolean(String x) {
        try {
            boolean result = Boolean.parseBoolean(x);
            return (result);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Id is not a Boolean");
        }
    }

    private int convertToInt(String x) {
        try {
            int result = Integer.parseInt(x);
            return (result);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Id is not an Integer");
        }
    }

    //MAPPINGS
    @GetMapping("/game/{gameid}")
    public Game sendsgameback(@PathVariable Long gameid) {
        return (this.gameservice.getGame(gameid));
    }

    @PutMapping("/game/{gameid}")
    public void updateGame(@RequestHeader(value = "token") String token, @PathVariable long gameId, @RequestBody Game game) {
        this.gameservice.update(gameId, game);
    }

    @PostMapping("/quit/{gameid}")
    public void quitgame(@RequestHeader(value = "token") String token, @PathVariable long gameId, @RequestParam long userId) {
        this.gameservice.leaveGame(userId, gameId);
    }

    @PutMapping("/godcard/{gameId}")
    public void selectGodPower(@RequestHeader(value = "token") String token, @PathVariable long gameId, @RequestBody AllId allId) {
        this.service.updateGodCard(allId);
    }

    @PostMapping("/waive/{gameId")
    public void cancelGodPower(@RequestHeader(value = "token") String token, @PathVariable long gameId, @RequestParam long userId) {
        this.gameservice.waiveGodCard(gameId, userId);
    }

    //ACTIONCONTROLLER

    @PostMapping("/movement/{gameId}")
    public ResponseEntity<?> moveWorker(@RequestHeader(value = "token") String token, @PathVariable long gameId, @RequestBody Move move) {
        return (new ResponseEntity<>(this.gameservice.move(move),HttpStatus.ACCEPTED));
    }

    @PostMapping("/building/{gameId}")
    public Game build(@RequestHeader(value = "token") String token, @PathVariable long gameId, @RequestBody Build build) {
        return (this.gameservice.build(build));
    }

    @GetMapping("/turn/{gameId}")
    public Game turn(@RequestHeader(value = "token") String token, @PathVariable long gameId) {
        return (this.gameservice.changeTurn(gameId));
    }

    @GetMapping("/highlightBuilding/{gameId}")
    public ArrayList<Field> highlightBuilding(@RequestHeader(value = "token") String token, @PathVariable long gameId, @RequestBody HighlightB highlightB) {
        return (this.gameservice.highlightB(highlightB, gameId));
    }

    @GetMapping("/highlightMovement/{gameId}")
    public ResponseEntity<?> highlightMovement(@RequestHeader(value = "token") String token, @PathVariable long gameId, @RequestBody HighlightM highlightM) {
        return (new ResponseEntity<>(this.gameservice.highlightM(highlightM, gameId),HttpStatus.ACCEPTED));
    }

}