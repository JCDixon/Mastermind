package com.mthree.guessthenumber.service;

import com.mthree.guessthenumber.models.Game;
import com.mthree.guessthenumber.models.Round;
import java.util.List;

/**
 *
 * @author john
 */
public interface GuessServiceLayer {

    public int createGame();

    public Round guessAnswer(int guess, int gameId)
            throws InvalidGuessFormatException, InvalidGameIdException, GameFinishedException;

    public Game getGame(int gameId) throws InvalidGameIdException;

    public List<Game> allGames();

    public List<Round> allRoundsByGameId(int gameId) throws InvalidGameIdException;

    public void resetGames();
}
