package ch.uzh.ifi.seal.soprafs19.service.service;

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
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.server.ResponseStatusException;

import java.awt.*;
import java.nio.charset.Charset;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Test class for the UserResource REST resource.
 *
 * @see UserService
 */
@WebAppConfiguration
@RunWith(SpringRunner.class)
@SpringBootTest(classes= Application.class)
@AutoConfigureMockMvc
public class UserServiceTest {

    @Autowired
    private MockMvc mockMvc;

    @Qualifier("userRepository")
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;


    public static final MediaType APPLICATION_JSON_UTF8 = new MediaType(
            MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

    //USEFUL FUNCTIONS
    public String get_Id_as_Sting_from_long(Long id){
        return(Long.toString(id));
    }

    @Test
    //checks if the right response is given when client updates the user
    public void checks_if_user_has_been_created() throws Exception {

        String creation = "{\"date_birth\" : \"01/01/1000\", \"name\" : \"testuser30\", \"username\" : \"testuser32\",\"password\" : \"testpassword\"}";

        this.mockMvc.perform(post("/users")
                .content(creation)
                .contentType(APPLICATION_JSON_UTF8)
                .header("test creation","creation"))
                .andExpect(status().isOk());
    }


    @Test
    //checks if a user can be created
    public void createUser() {
        Assert.assertNull(userRepository.findByUsername("testUsername"));

        User testUser = new User();
        testUser.setName("testName");
        testUser.setUsername("testUsername");
        testUser.setDateBirth("10/10/2010");
        testUser.setPassword("testPassword");

        User createdUser = userService.createUser(testUser);

        Assert.assertNotNull(createdUser.getToken());
        Assert.assertEquals(createdUser.getStatus(),UserStatus.ONLINE);
        Assert.assertEquals(createdUser, userRepository.findByToken(createdUser.getToken()));
    }

    @Test (expected =  ResponseStatusException.class)
    //Checks if a User with the same username can be created
    public void createUser2(){
        User testUser4 = new User();
        testUser4.setName("testName");
        testUser4.setUsername("testUsername4");
        testUser4.setDateBirth("10/10/2010");
        testUser4.setPassword("testPassword");
        User testUser7 = new User();
        testUser7.setName("testName");
        testUser7.setUsername("testUsername4");
        testUser7.setDateBirth("10/10/2010");
        testUser7.setPassword("testPassword");


        User createdUser4 = userService.createUser(testUser4);
        User createdUser7 = userService.createUser(testUser7);
    }


    @Test
    //checks if a User can be displayed
    public void display_user(){
        User testUser2 = new User();
        testUser2.setName("testName");
        testUser2.setUsername("testUsername2");
        testUser2.setDateBirth("10/10/2010");
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


    @Test
    //tests if the user_for_overview returns the right HTTP response
    public void test_if_get_for_Overview_works() throws Exception {

        User testUser7 = new User();
        testUser7.setName("testuser7");
        testUser7.setUsername("testuser7");
        testUser7.setDateBirth("10/10/2010");
        testUser7.setPassword("testPassword");

        testUser7 = userService.createUser(testUser7);

        String testuserIdstring=get_Id_as_Sting_from_long(testUser7.getId());
        this.mockMvc.perform(get("/user_for_overview").param("id",testuserIdstring).header("Access-Id",testuserIdstring)).andExpect(status().isOk());
    }

    @Test(expected =  ResponseStatusException.class)
    //checks if it throws an error if a user with a given User Id doesn't exist.
    public void display_user2(){
        User testUser5 = new User();
        testUser5.setName("testName");
        testUser5.setUsername("testUsername5");
        testUser5.setDateBirth("10/10/2010");
        testUser5.setPassword("testPassword");

        User createdUser5 = userService.createUser(testUser5);

        User result=userService.getUserbyID("20000");
    }




    @Test
    //tests if a user really is updated
    public void check_update(){
        User testUser3 = new User();
        testUser3.setName("testName");
        testUser3.setUsername("testUsername80");
        testUser3.setDateBirth("10/10/2010");
        testUser3.setPassword("testPassword");

        User createdUser3 = userService.createUser(testUser3);

        userService.changeUser(createdUser3.getId().toString(),"testusername90", "01/01/2111");

        String p=(userService.getUserbyID(createdUser3.getId().toString()).getUsername());
        String q=(userService.getUserbyID(createdUser3.getId().toString()).getDate_birth());

        Assert.assertEquals(p, "testusername90");
        Assert.assertEquals(q, ("01/01/2111"));
    }
}
