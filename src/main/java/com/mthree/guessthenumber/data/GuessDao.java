package com.mthree.guessthenumber.data;

import com.mthree.guessthenumber.models.Game;
import com.mthree.guessthenumber.models.Round;
import java.util.List;

/**
 *
 * @author john
 */
public interface GuessDao {

    public int createGame(int answer);

    public Round guessAnswer(int guess, String result, int gameId);

    public List<Game> allGames();

    public List<Round> allRoundsByGameId(int gameId);

    public Game getGame(int gameId);

    public void updateGameStatus(int gameId);

    public void deleteGame(int gameId);
}
