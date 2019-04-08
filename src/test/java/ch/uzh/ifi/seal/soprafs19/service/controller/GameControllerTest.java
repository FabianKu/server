package ch.uzh.ifi.seal.soprafs19.service.controller;

import ch.uzh.ifi.seal.soprafs19.Application;
import ch.uzh.ifi.seal.soprafs19.entity.User;
import ch.uzh.ifi.seal.soprafs19.repository.GameRepository;
import ch.uzh.ifi.seal.soprafs19.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebAppConfiguration
@RunWith(SpringRunner.class)
@SpringBootTest(classes= Application.class)
@AutoConfigureMockMvc
public class GameControllerTest {

    private User testUser;
    private String testUserJson;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserService userService;

    @Qualifier("gameRepository")
    @Autowired
    private GameRepository gameRepository;

    //SANTORINI GAME TESTS

    @Test
    public void testGetGame()throws Exception{

        this.mockMvc.perform(get("/game/1")
                .header("writing an answer, update invitations","invitations ID and answer"))
                .andExpect(status().isOk());
    }

    /*@Test
    public void testUpdateGame()throws Exception{

        //create game and save it into the database
        Game game= new Game();
        game.setGameId((long)1);
        gameRepository.save(game);

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson=ow.writeValueAsString(game);

        this.mockMvc.perform(put("/game/1")
                .content(requestJson)
                .header("update game","gameupdate"))
                .andExpect(status().isOk());
    }*/


}