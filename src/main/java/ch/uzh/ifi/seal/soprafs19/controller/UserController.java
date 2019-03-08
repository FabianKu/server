package ch.uzh.ifi.seal.soprafs19.controller;

import ch.uzh.ifi.seal.soprafs19.entity.User;
import ch.uzh.ifi.seal.soprafs19.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class UserController {

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
        return(service.check_password(username,password));
    }

    @GetMapping("/get_user")
    User get_the_user(@RequestParam String username){
        return(service.getUserbyusername(username));
    }


    @GetMapping("/user_for_overview")
    User get_the_user_for_overview(@RequestParam String id){
        User tryUser=this.service.getUserbyID(id);
        return (tryUser);
    }

    @GetMapping("/password_to_edit")
    Boolean check_password_to_edit(@RequestParam String id,@RequestParam String password){
        return(service.check_password_to_edit(id,password));
    }

    @CrossOrigin
    @PutMapping(value = "/change/{userId}")
    void changeUser(@PathVariable String userId, @RequestBody Map<String, String> json){
        String user_name=json.get("username");
        String date_of_birth=json.get("birthday");
        service.change_user(userId, user_name, date_of_birth);
    }
}


