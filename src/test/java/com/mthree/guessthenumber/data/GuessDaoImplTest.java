package com.mthree.guessthenumber.data;

import com.mthree.guessthenumber.models.Game;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 *
 * @author john
 */
@SpringBootTest
public class GuessDaoImplTest {

    @Autowired
    private GuessDao dao;

    int gameId1;
    int gameId2;

    public GuessDaoImplTest() {

    }

    @BeforeEach
    public void setUp() {
        gameId1 = dao.createGame(1234);
        gameId2 = dao.createGame(4321);
    }

    @AfterEach
    public void tearDown() {
        List<Game> allGames = dao.allGames();
        for (Game g : allGames) {
            dao.deleteGame(g.getGameId());
        }
    }

    @Test
    public void testGetAllGames() {
        List<Game> allGames = dao.allGames();

        assertEquals(2, allGames.size(), "Size of the retrieved list of Games should be 2");

        assertEquals(gameId1, allGames.get(0).getGameId(),
                "Assert that first game placed in database is the first retrieved with correct gameId");
        assertEquals(1234, allGames.get(0).getAnswer(),
                "Assert that first game placed in database is the first retrieved with answer 1234");

        assertEquals(gameId2, allGames.get(1).getGameId(),
                "Assert that first game placed in database is the first retrieved with correct gameId");
        assertEquals(4321, allGames.get(1).getAnswer(),
                "Assert that first game placed in database is the first retrieved with answer 4321");
    }
}
