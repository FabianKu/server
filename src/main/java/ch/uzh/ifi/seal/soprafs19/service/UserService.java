package ch.uzh.ifi.seal.soprafs19.service;

import ch.uzh.ifi.seal.soprafs19.constant.UserStatus;
import ch.uzh.ifi.seal.soprafs19.entity.User;
import ch.uzh.ifi.seal.soprafs19.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Calendar;
import java.text.SimpleDateFormat;


import java.util.UUID;

@Service
@Transactional
public class UserService {

    private final Logger log = LoggerFactory.getLogger(UserService.class);

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
            return(null);
        }

        //added set function to store the creation date;
        set_creation_date(newUser);
        userRepository.save(newUser);
        log.debug("Created Information for User: {}", newUser);
        return newUser;

    }

    private void set_creation_date(User newUser){
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


}
