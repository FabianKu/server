package ch.uzh.ifi.seal.soprafs19.service;

import ch.uzh.ifi.seal.soprafs19.Application;
import ch.uzh.ifi.seal.soprafs19.constant.UserStatus;
import ch.uzh.ifi.seal.soprafs19.entity.User;
import ch.uzh.ifi.seal.soprafs19.repository.UserRepository;
import ch.uzh.ifi.seal.soprafs19.service.UserService;
import org.assertj.core.api.Assertions;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.client.HttpClientErrorException;

/**
 * Test class for the UserResource REST resource.
 *
 * @see UserService
 */
@WebAppConfiguration
@RunWith(SpringRunner.class)
@SpringBootTest(classes= Application.class)
public class UserServiceTest {


    @Qualifier("userRepository")
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Test
    public void createUser() {
        Assert.assertNull(userRepository.findByUsername("testUsername"));

        User testUser = new User();
        testUser.setName("testName");
        testUser.setUsername("testUsername");
        testUser.setDate_birth("10/10/2010");
        testUser.setPassword("testPassword");

        User createdUser = userService.createUser(testUser);

        Assert.assertNotNull(createdUser.getToken());
        Assert.assertEquals(createdUser.getStatus(),UserStatus.ONLINE);
        Assert.assertEquals(createdUser, userRepository.findByToken(createdUser.getToken()));

    }

    @Test (expected =  HttpClientErrorException.class)
    public void createUser2(){
        User testUser4 = new User();
        testUser4.setName("testName");
        testUser4.setUsername("testUsername4");
        testUser4.setDate_birth("10/10/2010");
        testUser4.setPassword("testPassword");
        User testUser7 = new User();
        testUser7.setName("testName");
        testUser7.setUsername("testUsername4");
        testUser7.setDate_birth("10/10/2010");
        testUser7.setPassword("testPassword");


        User createdUser4 = userService.createUser(testUser4);
        User createdUser7 = userService.createUser(testUser7);
    }


    @Test
    public void display_user(){
        User testUser2 = new User();
        testUser2.setName("testName");
        testUser2.setUsername("testUsername2");
        testUser2.setDate_birth("10/10/2010");
        testUser2.setPassword("testPassword");

        User createdUser2 = userService.createUser(testUser2);


        User getUser = userService.getUserbyusername(createdUser2.getUsername());

        Assert.assertNotNull(getUser);
        Assert.assertEquals(getUser.getId(),createdUser2.getId());

        Assert.assertNotNull(getUser.getId());
        Assert.assertNotNull(getUser.getUsername());
        Assert.assertNotNull(getUser.getCreation_date());

        Assert.assertNotNull(getUser.getDate_birth());

        System.out.print(getUser.getId());
        System.out.print(getUser.getUsername());
        System.out.print(getUser.getCreation_date());

        System.out.print(getUser.getDate_birth());
    }

    @Test(expected =  HttpClientErrorException.class)
    public void display_user2(){
        User testUser5 = new User();
        testUser5.setName("testName");
        testUser5.setUsername("testUsername5");
        testUser5.setDate_birth("10/10/2010");
        testUser5.setPassword("testPassword");

        User createdUser5 = userService.createUser(testUser5);

        User result=userService.getUserbyID("20000");
    }

    @Test
    public void check_update(){
        User testUser3 = new User();
        testUser3.setName("testName");
        testUser3.setUsername("testUsername3");
        testUser3.setDate_birth("10/10/2010");
        testUser3.setPassword("testPassword");

        User createdUser3 = userService.createUser(testUser3);

        userService.change_user(createdUser3.getId().toString(),"testusername8", "01/01/2111");

        String p=(userService.getUserbyID(createdUser3.getId().toString()).getUsername());
        String q=(userService.getUserbyID(createdUser3.getId().toString()).getDate_birth());

        Assert.assertEquals(p, "testusername8");
        Assert.assertEquals(q, ("01/01/2111"));
    }



    @Test (expected =  HttpClientErrorException.class)
    public void check_update2(){
        User testUser6 = new User();
        testUser6.setName("testName");
        testUser6.setUsername("testUsername6");
        testUser6.setDate_birth("10/10/2010");
        testUser6.setPassword("testPassword");

        User createdUser6 = userService.createUser(testUser6);

        userService.change_user("2000","testusername5", "01/01/2111");
    }


}
