package ch.uzh.ifi.seal.soprafs19.service;

import ch.uzh.ifi.seal.soprafs19.constant.UserStatus;
import ch.uzh.ifi.seal.soprafs19.entity.User;
import ch.uzh.ifi.seal.soprafs19.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.client.HttpClientErrorException;

import java.text.ParseException;
import java.util.Date;
import java.util.Calendar;
import java.text.SimpleDateFormat;


import java.util.UUID;

@Service
@Transactional
public class UserService {

    private final UserRepository userRepository;


    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Iterable<User> getUsers() {
        return this.userRepository.findAll();
    }

    public User createUser(User newUser) {
        newUser.setToken(UUID.randomUUID().toString());
        newUser.setStatus(UserStatus.ONLINE);

        //find out if username already exists
        if(userRepository.existsByUsername(newUser.getUsername())) {
            throw new HttpClientErrorException(HttpStatus.CONFLICT, "Username already exists");
        }

        //test if the entered date is really a date
        try {
            Date date1 = new SimpleDateFormat("dd/mm/yyyy").parse(newUser.getDate_birth());
        }catch(ParseException err){
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "The date wasn't entered properly");
        };

        //added set function to store the creation date;
        set_creation_date(newUser);
        userRepository.save(newUser);
        return newUser;

    }

    public void set_creation_date(User newUser){
            SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//dd/MM/yyyy
            Date now = new Date();
            String time_date = sdfDate.format(now);
            newUser.setCreation_date(time_date);
    }

    public boolean check_password(String username,String password){
        User tryUser=userRepository.findByUsername(username);
        if(tryUser.getPassword().equals(password)){return (true);}
        else{return (false);}
    }

    public User getUserbyusername(String name){
        return(userRepository.findByUsername(name));
    }

    public User getUserbyID(String id){
        long long_id=check_with_id_if_User_exists(id);
        return(userRepository.findById(long_id));
    }

    //takes the id as a String and converts it to a long.
    //If the User with the id exists it returns the id as a long
    //else it throws a 404 error
    public long check_with_id_if_User_exists(String id){
        long long_id = Long.parseLong(id);
        if (!userRepository.existsById(long_id)){
            throw new HttpClientErrorException(HttpStatus.NOT_FOUND, "ID wasn't found");
        }
        return (long_id);
    }


    public boolean check_password_to_edit(String id, String password){
        User realUser=getUserbyID(id);
        if(realUser.getPassword().equals(password)){
            return (true);
        }
        else{
            return (false);
        }
    }


    public void change_user(String id, String uname, String birthday) {
        User realuser = getUserbyID(id);
        if (uname != null && realuser != null) {
            realuser.setUsername(uname);
        }
        if (birthday != null && realuser != null) {
            try {
                Date date1 = new SimpleDateFormat("dd/mm/yyyy").parse(birthday);
                if (date1 != null) {
                    realuser.setDate_birth(birthday);
                }
            } catch (ParseException err) {
                throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "The date wasn't entered properly");
            }
            ;
        }
    }

}
