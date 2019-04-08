package ch.uzh.ifi.seal.soprafs19.service.controller;

import ch.uzh.ifi.seal.soprafs19.Application;
import ch.uzh.ifi.seal.soprafs19.entity.User;
import ch.uzh.ifi.seal.soprafs19.model.Invitation;
import ch.uzh.ifi.seal.soprafs19.repository.GameRepository;
import ch.uzh.ifi.seal.soprafs19.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
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

import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebAppConfiguration
@RunWith(SpringRunner.class)
@SpringBootTest(classes= Application.class)
@AutoConfigureMockMvc
public class InvitationControllerTest {

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
    private static long intToLong(int a){
        long b=(long)a;
        return(b);
    }

    private static String asJsonString(final Object obj) {
        try {
            final ObjectMapper mapper = new ObjectMapper();
            final String jsonContent = mapper.writeValueAsString(obj);
            return jsonContent;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testInvitation() throws Exception {

        //create a new User (invitee)
        User testUser3 = new User();
        testUser3.setName("testName");
        testUser3.setUsername("testUsername3");
        testUser3.setDateBirth("10/10/2010");
        testUser3.setPassword("testPassword");
        User newUser3 = userService.createUser(testUser3);

        //create a new User (inviter)
        User testUser7 = new User();
        testUser7.setName("testName");
        testUser7.setUsername("testUsername7");
        testUser7.setDateBirth("10/10/2010");
        testUser7.setPassword("testPassword");
        User newUser7 = userService.createUser(testUser7);

        //creating an invitation-Object
        long inviterId=newUser7.getId();
        long receiverId=newUser3.getId();
        Invitation invitation=new Invitation(inviterId,receiverId, null);

        this.mockMvc.perform(post("/invitation")
                .content(asJsonString(invitation))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .header("token", newUser3.getToken())
                .header("sending an invitation", "invitation ID's"))
                .andExpect(status().isOk());
    }


    @Test
    public void testGetInvitation() throws Exception {
        User testUser4 = new User();
        testUser4.setName("testName");
        testUser4.setUsername("testUsername4");
        testUser4.setDateBirth("10/10/2010");
        testUser4.setPassword("testPassword");

        User newUser4 = userService.createUser(testUser4);

        //getting the id
        long id = newUser4.getId();

        this.mockMvc.perform(get("/invitation/" + id)
                .header("token", newUser4.getToken())
                .header("test getting all invitations", "invitations"))
                .andExpect(status().isOk());
    }

    @Test
    public void testAnswerInvitation() throws Exception {

        //creating an invitation-Object
        long inviterId=intToLong(1);
        long inviteeId=intToLong(1);
        Invitation answer=new Invitation(inviterId,inviteeId, false);

        //write the jsonfile
        String jsonfile = "{\"inviterId\" : \"5\", \"inviteeId\" : \"7\", \"answer\" : \"true\"}";

        //create a new User
        User testUser8 = new User();
        testUser8.setName("testName");
        testUser8.setUsername("testUsername8");
        testUser8.setDateBirth("10/10/2010");
        testUser8.setPassword("testPassword");
        User newUser8 = userService.createUser(testUser8);

        this.mockMvc.perform(put("/invitation")
                .content(asJsonString(answer))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .header("token", newUser8.getToken())
                .header("writing an answer, update invitations", "invitations ID and answer"))
                .andExpect(status().isOk());
    }
}