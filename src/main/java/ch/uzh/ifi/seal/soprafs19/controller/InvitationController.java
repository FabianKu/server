package ch.uzh.ifi.seal.soprafs19.controller;

import ch.uzh.ifi.seal.soprafs19.model.Invitation;
import ch.uzh.ifi.seal.soprafs19.service.GameService;
import ch.uzh.ifi.seal.soprafs19.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.LinkedHashSet;


@RestController
public class InvitationController {

    //LOGGER
    private final Logger log = LoggerFactory.getLogger(UserService.class);

    //Constants
    private final UserService service;
    private final GameService gameservice;

    //Constructor
    InvitationController(UserService service, GameService gameservice) {
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
    @PostMapping("/invitation")
    public void sendInvitation(@RequestHeader(value = "token") String token, @RequestBody Invitation invitation)throws Exception{
        this.service.addInvitation(invitation);

        //token check
    }

    @GetMapping("/invitation/{userId}")
    public LinkedHashSet<Long> seeInvitations(@RequestHeader(value = "token") String token, @PathVariable long userId){
        return(this.service.get_all_Invitations(userId));
    }

    @PutMapping("/invitation")
    public void senderResponse(@RequestHeader(value = "token") String token, @RequestBody Invitation answer)throws Exception{
        this.service.invitationResponse(answer);
    }
}
