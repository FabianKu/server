package ch.uzh.ifi.seal.soprafs19.controller;

import ch.uzh.ifi.seal.soprafs19.entity.User;
import ch.uzh.ifi.seal.soprafs19.model.Invitation;
import ch.uzh.ifi.seal.soprafs19.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashSet;
import java.util.Map;


import java.util.logging.ConsoleHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
public class UserController {

    Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    private final UserService service;

    UserController(UserService service) {
        this.service = service;
    }

    @GetMapping("/users")
    Iterable<User> all() {
        return service.getUsers();
    }


    @PostMapping("/users")
    User createUser(@RequestBody User newUser) {
        return this.service.createUser(newUser);
    }

    @GetMapping("/password")
    Boolean check_password(@RequestParam String username,@RequestParam String password){
        return(service.checkPassword(username,password));
    }

    @GetMapping("/get_user")
    User get_the_user(@RequestParam String username){
        return(service.getUserbyusername(username));
    }

    @CrossOrigin
    @GetMapping("/user_for_overview")
    User get_the_user_for_overview(@RequestParam (value="id") String id){
        logger.info("get the user with id"+ id);
        User tryUser=this.service.getUserbyID(id);
        logger.info("got the user and returns it: " +tryUser.getUsername());
        return (tryUser);
    }

    @GetMapping("/password_to_edit")
    Boolean check_password_to_edit(@RequestParam String id,@RequestParam String password){
        return(service.checkPasswordToEdit(id,password));
    }

    @CrossOrigin
    @PutMapping(value = "/change/{userId}")
    void changeUser(@PathVariable String userId, @RequestBody Map<String, String> json){
        String user_name=json.get("username");
        String date_of_birth=json.get("birthday");
        service.changeUser(userId, user_name, date_of_birth);
    }

    //INVITATIONS
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

    //MATCHMAKING
    @PostMapping("/matchmaking")
    public void startMatchmaking(@RequestHeader(value = "token") String token, @RequestBody long userId){
        this.service.startMatchmaking(userId);
    }

    @GetMapping("/matchmaking")
    public ResponseEntity<?> isInGame(@RequestBody long userId){
        if(this.service.isInGame(userId)){
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}


