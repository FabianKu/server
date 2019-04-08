package ch.uzh.ifi.seal.soprafs19.controller;


import ch.uzh.ifi.seal.soprafs19.service.GameService;
import ch.uzh.ifi.seal.soprafs19.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;


@RestController
public class MatchMakingController {

    //LOGGER
    private final Logger log = LoggerFactory.getLogger(UserService.class);

    //Constants
    private final UserService service;
    private final GameService gameservice;

    //Constructor
    MatchMakingController(UserService service, GameService gameservice) {
        this.service = service;
        this.gameservice = gameservice;
    }

    //USEFUL FUNCTIONS
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
    @PostMapping("/matchmaking")
    public void startmatchmaking(@RequestHeader(value = "token") String token, @RequestBody long userId){
        this.service.startMatchmaking(userId);
    }
}
