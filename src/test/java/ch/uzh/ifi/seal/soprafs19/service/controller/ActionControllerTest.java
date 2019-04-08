package ch.uzh.ifi.seal.soprafs19.service.controller;

import ch.uzh.ifi.seal.soprafs19.Application;
import ch.uzh.ifi.seal.soprafs19.entity.Game;
import ch.uzh.ifi.seal.soprafs19.entity.Player;
import ch.uzh.ifi.seal.soprafs19.entity.User;
import ch.uzh.ifi.seal.soprafs19.model.Build;
import ch.uzh.ifi.seal.soprafs19.model.Move;
import ch.uzh.ifi.seal.soprafs19.model.Turn;
import ch.uzh.ifi.seal.soprafs19.model.Worker;
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
import java.util.ArrayList;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebAppConfiguration
@RunWith(SpringRunner.class)
@SpringBootTest(classes= Application.class)
@AutoConfigureMockMvc
public class ActionControllerTest {

    //ATTRIBUTES
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
    public void testMovement()throws Exception{
        //create game and save it into the database
        Turn turn=new Turn();
        Player player1=new Player();
        Player player2=new Player();
        ArrayList board=new ArrayList();
        Game game= new Game(player1,player2,board,1,turn);
        game.setGameId((long)1);
        gameRepository.save(game);

        //create a new User
        User testUser6 = new User();
        testUser6.setName("testName");
        testUser6.setUsername("testUsername6");
        testUser6.setDateBirth("10/10/2010");
        testUser6.setPassword("testPassword");
        User newUser6 = userService.createUser(testUser6);

        //create a Worker
        Worker worker=new Worker(1,1);

        //create a Move
        long gameId=intToLong(1);
        Move move= new Move(5,7,worker,gameId);

        this.mockMvc.perform(post("/movement/1")
                .content(asJsonString(move))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .header("token",newUser6.getToken())
                .header("building","building"))
                .andExpect(status().isOk());
    }

    @Test
    public void testBuilding()throws Exception{
        //create game and save it into the database
        Turn turn=new Turn();
        Player player1=new Player();
        Player player2=new Player();
        ArrayList board=new ArrayList();
        Game game= new Game(player1,player2,board,1,turn);
        game.setGameId((long)1);
        gameRepository.save(game);

        //create a new User
        User testUser5 = new User();
        testUser5.setName("testName");
        testUser5.setUsername("testUsername5");
        testUser5.setDateBirth("10/10/2010");
        testUser5.setPassword("testPassword");
        User newUser5 = userService.createUser(testUser5);

        //create a Worker
        Worker worker=new Worker(1,1);

        //create a Move
        long gameId=intToLong(1);
        Build build= new Build(5,7,worker,gameId);

        this.mockMvc.perform(post("/building/1")
                .content(asJsonString(build))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .header("token",newUser5.getToken())
                .header("building","building"))
                .andExpect(status().isOk());
    }
}
