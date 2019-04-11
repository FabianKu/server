package ch.uzh.ifi.seal.soprafs19.service.controller;

import ch.uzh.ifi.seal.soprafs19.Application;
import ch.uzh.ifi.seal.soprafs19.entity.*;
import ch.uzh.ifi.seal.soprafs19.model.HighlightM;
import ch.uzh.ifi.seal.soprafs19.repository.UserRepository;
import ch.uzh.ifi.seal.soprafs19.service.GameService;
import ch.uzh.ifi.seal.soprafs19.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.After;
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

import java.time.LocalDate;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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

    private Game testGame;
    private Player player1;
    private Player player2;
    private User testUser;
    private User testUser2;
    private User realUser;
    private User realUser2;

    @Autowired
    private UserService userService;

    @Qualifier("userRepository")
    @Autowired
    private UserRepository userRepository;

    @Qualifier("gameService")
    @Autowired
    private GameService gameService;

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

    private Field findFieldWithWorker(Worker worker, long gameId){
        List<Field> boardOfCurrentGame=testGame.getBoard();
        //iterate over all fields to find the Field with the worker
        for (Field currentField : boardOfCurrentGame) {
            if(currentField.getWorker().equals(worker)){
                return(currentField);
            }
        }
        throw new RuntimeException("No worker with Id " + worker.getWorkerId() + " in game with Id " + gameId + " was found");
    }

    private Field findFieldWithCoordinates(int posX, int posY, long gameId){
        Game game = testGame;
        List<Field> boardOfCurrentGame=game.getBoard();
        //iterate over all fields to find the Field with the worker
        for (Field currentField : boardOfCurrentGame) {
            //get Coordinates
            if(currentField.getPosY()==posY && currentField.getPosX()==posX){
                return(currentField);
            }
        }
        throw new RuntimeException("No field with posx "+posX+" and posY " +posY+ " was found");
    }

    //BEFORE
    @Before
    public void setUp() {
        Turn turn=new Turn();
        testGame= new Game(false, turn);
        this.gameService.createGame(testGame);

        testUser = new User();
        testUser.setName("testName");
        testUser.setUsername("testUsername11000");
        testUser.setDateBirth("17/10/2017");
        testUser.setPassword("testPassword");
        realUser=userService.createUser(testUser);

        testUser2 = new User();
        testUser2.setName("testName");
        testUser2.setUsername("testUsername11002");
        testUser2.setDateBirth("17/10/2017");
        testUser2.setPassword("testPassword");
        realUser2=userService.createUser(testUser2);

        player1=new Player(realUser.getId());
        player1.setPlayerId((long)1);
        player2=new Player(realUser2.getId());
        player2.setPlayerId((long)2);
        testGame.setPlayers(player1,player2);
        player1.setGame(testGame);
        player2.setGame(testGame);
    }

    @After
    public void deleteGamePlayerUser(){
        this.gameService.deleteGame(testGame);
        userRepository.delete(realUser);
        userRepository.delete(realUser2);
    }

    //TESTS
    @Test
    public void testMovement()throws Exception{
        //create a Worker
        Worker worker=new Worker(player1.getPlayerId());

        //create a Move
        long gameId=intToLong(1);
        Move move= new Move(5,4,worker,testGame.getGameId());

        this.mockMvc.perform(post("/movement/"+testGame.getGameId())
                .content(asJsonString(move))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .header("token",realUser.getToken())
                .header("building","building"))
                .andExpect(status().isAccepted());
    }

    @Test
    public void testBuilding()throws Exception{
        //create a Worker
        Worker worker=new Worker(player1.getPlayerId());

        //create a Build
        long gameId=intToLong(1);
        Build build= new Build(5,7,worker,testGame.getGameId());

        this.mockMvc.perform(post("/building/"+testGame.getGameId())
                .content(asJsonString(build))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .header("token",realUser.getToken())
                .header("building","building"))
                .andExpect(status().isOk());
    }

    @Test
    public void testHighlighMovement()throws Exception{
        //create a worker
        Worker worker=new Worker(player1.getPlayerId());
        //place worker on field 1/1
        Field currentField=findFieldWithCoordinates(1,1,testGame.getGameId());
        currentField.setWorker(worker);
        //create a highlightM object
        HighlightM highlightM=new HighlightM(worker);

        MvcResult result= this.mockMvc.perform(get("/highlightMovement/"+testGame.getGameId())
                .content(asJsonString(highlightM))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .header("token",realUser.getToken())
                .header("highlightMovements","highlight"))
                .andExpect(status().isAccepted())
                .andReturn();

        String JsonResult=result.getResponse().getContentAsString().toString();
        String rightResult="[{\"posX\":1,\"posY\":2,\"heightOfBuilding\":0},{\"posX\":2,\"posY\":1,\"heightOfBuilding\":0},{\"posX\":2,\"posY\":2,\"heightOfBuilding\":0}]".toString();

        Assert.assertTrue(JsonResult.equals(rightResult));
    }

    @Test
    public void testWorkerPlacement()throws Exception{
        Worker worker = new Worker(player1.getPlayerId());
        Move move = new Move(1 ,1, worker, testGame.getGameId());

        this.mockMvc.perform(post("/movement/"+testGame.getGameId())
                .content(asJsonString(move))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .header("token",realUser.getToken())
                .header("movement","movement"))
                .andExpect(status().isAccepted());

        //Conflict when placing on the same field
        /*Move move2 = new Move(1 ,1, worker, testGame.getGameId());

        this.mockMvc.perform(post("/movement/1")
                .content(asJsonString(move2))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .header("token",realUser.getToken())
                .header("movement","movement"))
                .andExpect(status().isConflict());*/
    }

}
