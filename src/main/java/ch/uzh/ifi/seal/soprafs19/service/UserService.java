package ch.uzh.ifi.seal.soprafs19.service;

import ch.uzh.ifi.seal.soprafs19.constant.UserStatus;
import ch.uzh.ifi.seal.soprafs19.entity.Player;
import ch.uzh.ifi.seal.soprafs19.entity.User;
import ch.uzh.ifi.seal.soprafs19.model.AllId;
import ch.uzh.ifi.seal.soprafs19.model.Invitation;
import ch.uzh.ifi.seal.soprafs19.repository.PlayerRepository;
import ch.uzh.ifi.seal.soprafs19.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.server.ResponseStatusException;

import java.text.ParseException;
import java.util.Date;
import java.util.Calendar;
import java.text.SimpleDateFormat;


import java.util.LinkedHashSet;
import java.util.UUID;

@Service
@Transactional
public class UserService {

    //CONSTANTS
    private final Logger log = LoggerFactory.getLogger(UserService.class);
    private final UserRepository userRepository;
    private final PlayerRepository playerRepository;

    //CONSTRUCTOR
    @Autowired
    public UserService(UserRepository userRepository, PlayerRepository playerRepository) {
        this.userRepository = userRepository;
        this.playerRepository=playerRepository;
    }

    public Iterable<User> getUsers() {
        return this.userRepository.findAll();
    }

    public User createUser(User newUser) {
        newUser.setToken(UUID.randomUUID().toString());
        newUser.setStatus(UserStatus.ONLINE);

        //find out if username already exists
        if(userRepository.existsByUsername(newUser.getUsername())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Username already exists");
        }

        //test if the entered date is really a date
        try {
            Date date1 = new SimpleDateFormat("dd/mm/yyyy").parse(newUser.getDate_birth());
        }catch(ParseException err){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The date wasn't entered properly");
        }

        //added set function to store the creation date;
        setCreationDate(newUser);
        userRepository.save(newUser);
        return newUser;

    }

    public void setCreationDate(User newUser){
            SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//dd/MM/yyyy
            Date now = new Date();
            String time_date = sdfDate.format(now);
            newUser.setCreation_date(time_date);
    }

    public boolean checkPassword(String username,String password){
        User tryUser=userRepository.findByUsername(username);
        if(tryUser.getPassword().equals(password)){return (true);}
        else{return (false);}
    }

    public User getUserbyusername(String name){
        return(userRepository.findByUsername(name));
    }

    public User getUserbyID(String id){
        long long_id=checkWithIdIfUserExists(id);
        return(userRepository.findById(long_id));
    }

    //takes the id as a String and converts it to a long.
    //If the User with the id exists it returns the id as a long
    //else it throws a 404 error
    public long checkWithIdIfUserExists(String id){
        long long_id = Long.parseLong(id);
        if (!userRepository.existsById(long_id)){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "ID wasn't found");
        }
        return (long_id);
    }


    public boolean checkPasswordToEdit(String id, String password){
        User realUser=getUserbyID(id);
        if(realUser.getPassword().equals(password)){
            return (true);
        }
        else{
            return (false);
        }
    }


    public void changeUser(String id, String uname, String birthday) {
        User realuser = getUserbyID(id);
        if (uname != null && realuser != null) {
            realuser.setUsername(uname);
        }
        if (birthday != null && realuser != null) {
            try {
                Date date1 = new SimpleDateFormat("dd/mm/yyyy").parse(birthday);
                if (date1 != null) {
                    realuser.setDateBirth(birthday);
                }
            } catch (ParseException err) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The date wasn't entered properly");
            }
            ;
        }
    }


    //INVITATIONS
    //sends an answer back from the invitee to the inviter
    public void invitationResponse(Invitation invitation){
        //give the inviter the answer of the invitee
    }

    //adds the invitation from the inviter to the list of invitations of the invitee
    public void addInvitation(Invitation invitation){
        User receiverUser = this.userRepository.findById(invitation.getInviteeId());
        receiverUser.receiveInvitation(invitation.getInviterId());
    }

    //get all invitations that a certain User has received
    public LinkedHashSet<Long> get_all_Invitations(long userId){
        User currentUser=this.userRepository.findById(userId);
        return(currentUser.getAllInvitations());
    }

    //INVITATION ENDS
    //MATCHMAKING

    //a User with an userId started looking for other Users to play with
    public void startMatchmaking(long userId){
        //start looking for other players
    }

    //MATCHMAKING ENDS
    //GOD CARDS

    //gives a Player a Godcard
    public void updateGodCard(AllId allId){
        Player currentPlayer= this.playerRepository.findById(allId.getPlayerId());
        //update Godcard of currentPlayer
    }



}
