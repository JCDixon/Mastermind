package com.mthree.guessthenumber.service;

import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 *
 * @author john
 */
@SpringBootTest
public class GuessServiceLayerTest {

    @Autowired
    private GuessServiceLayer service;

    public GuessServiceLayerTest() {
    }

    @BeforeEach
    public void setUp() {

    }

    @AfterEach
    public void tearDown() {
        service.resetGames();
    }

    @Test
    public void testGetGameByCorrectId() {
        try {
            int gameId = service.createGame();
            assertNotNull(service.getGame(gameId), "Game that was created and retrieved should not be null");
        } catch (InvalidGameIdException ex) {
            fail("No exception should have been thrown:: " + ex.getMessage());
        }
    }

    @Test
    public void testGetGameByIncorrectId() {
        try {
            int gameId = service.createGame();
            service.getGame(1243);
            fail("InvalidGameIdException should have been thrown");
        } catch (InvalidGameIdException ex) {
            //Test succeeds
        }
    }

    @Test
    public void testStatusSolvedGame() {
        try {
            int gameId = service.createGame();
            int answer = service.getGame(gameId).getAnswer();
            service.guessAnswer(answer, gameId);
            assertTrue(service.getGame(gameId).getStatus(), "Game status should be finished");

        } catch (InvalidGameIdException | InvalidGuessFormatException | GameFinishedException ex) {
            fail("No exception should have been thrown:: " + ex.getMessage());
        }
    }

    @Test
    public void testStatusUnsolvedGame() {
        try {
            int gameId = service.createGame();
            int answer = service.getGame(gameId).getAnswer();
            service.guessAnswer(((((answer%10)/2)+2)+1230), gameId);
            assertFalse(service.getGame(gameId).getStatus(), "Game status should not be finished");

        } catch (InvalidGameIdException | InvalidGuessFormatException | GameFinishedException ex) {
            fail("No exception should have been thrown:: " + ex.getMessage());
        }
    }

    @Test
    public void testGuessSolvedGame() {
        try {
            int gameId = service.createGame();
            int answer = service.getGame(gameId).getAnswer();
            service.guessAnswer(answer, gameId);
            assertTrue(service.getGame(gameId).getStatus(), "Game status should be finished");
            service.guessAnswer(answer, gameId);

        } catch (InvalidGameIdException | InvalidGuessFormatException ex) {
            fail("Incorrect exception was thrown:: " + ex.getMessage());
        } catch (GameFinishedException ex) {
            //Correct exception thrown when trying to guess an already finished game
        }
    }
}
