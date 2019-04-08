package ch.uzh.ifi.seal.soprafs19.service.controller;

import ch.uzh.ifi.seal.soprafs19.Application;
import ch.uzh.ifi.seal.soprafs19.entity.User;
import ch.uzh.ifi.seal.soprafs19.repository.GameRepository;
import ch.uzh.ifi.seal.soprafs19.service.UserService;
import org.junit.Assert;
import org.junit.Before;
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
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.server.ResponseStatusException;

import java.nio.charset.Charset;
import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebAppConfiguration
@RunWith(SpringRunner.class)
@SpringBootTest(classes= Application.class)
@AutoConfigureMockMvc
public class UserControllerTest {

    //ATTRIBUTES
    private User testUser;
    private String testUserJson;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserService userService;

    @Qualifier("gameRepository")
    @Autowired
    private GameRepository gameRepository;

    //USEFUL FUNCTIONS
    public String get_Id_as_Sting_from_long(Long id){
        return(Long.toString(id));
    }

    //TESTS
    public static final MediaType APPLICATION_JSON_UTF8 = new MediaType(
            MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

    @Test
    //checks if the right response is given when client updates the user
    public void checks_if_user_has_been_created() throws Exception {

        String creation = "{\"date_birth\" : \"01/01/1000\", \"name\" : \"testuser30\", \"username\" : \"testuser30\",\"password\" : \"testpassword\"}";

        this.mockMvc.perform(post("/users")
                .content(creation)
                .contentType(APPLICATION_JSON_UTF8)
                .header("test creation","creation"))
                .andExpect(status().isOk());
    }


    @Test
    //checks if the right response is given when client updates the user
    public void checks_if_user_is_updated() throws Exception {

        User testUser8 = new User();
        testUser8.setName("testName");
        testUser8.setUsername("testUsername50");
        testUser8.setDateBirth("10/10/2010");
        testUser8.setPassword("testPassword");

        User createdUser8 = userService.createUser(testUser8);

        String update = "{\"username\" : \"change\", \"birthday\" : \"01/01/1000\"}";

        this.mockMvc.perform(put("/change/" + get_Id_as_Sting_from_long(createdUser8.getId()))
                .content(update)
                .contentType(APPLICATION_JSON_UTF8)
                .header("test change","change test"))
                .andExpect(status().isOk());
    }

    @Test (expected =  ResponseStatusException.class)
    public void check_update2(){
        User testUser6 = new User();
        testUser6.setName("testName");
        testUser6.setUsername("testUsername6");
        testUser6.setDateBirth("10/10/2010");
        testUser6.setPassword("testPassword");

        User createdUser6 = userService.createUser(testUser6);

        userService.changeUser("2000","testusername5", "01/01/2111");
    }

    @Test
    //tests if an error is given back if a user with a certain user Id doesn't exist.
    public void if_error_is_given_back_if_a_non_existing_id_is_entered() throws Exception {

        this.mockMvc.perform(get("/user_for_overview").param("id","20000").header("Access-Id","20000")).andExpect(status().is4xxClientError());
    }

    @Test
    //Checks if a User with the same username can be created
    public void checks_if_user_with_an_existing_username_can_be_created() throws Exception {

        User testUser40 = new User();
        testUser40.setName("testName");
        testUser40.setUsername("testUsername40");
        testUser40.setDateBirth("10/10/2010");
        testUser40.setPassword("testPassword");

        User createdUser40 = userService.createUser(testUser40);


        String creation = "{\"date_birth\" : \"01/01/1000\", \"name\" : \"testuser30\", \"username\" : \"testUsername40\",\"password\" : \"testpassword\"}";

        this.mockMvc.perform(post("/users")
                .content(creation)
                .contentType(APPLICATION_JSON_UTF8)
                .header("test creation","creation"))
                .andExpect(status().is4xxClientError());
    }
}