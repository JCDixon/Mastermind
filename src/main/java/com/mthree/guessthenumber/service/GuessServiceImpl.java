package com.mthree.guessthenumber.service;

import com.mthree.guessthenumber.data.GuessDao;
import com.mthree.guessthenumber.models.Game;
import com.mthree.guessthenumber.models.Round;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author john
 */
@Service
public class GuessServiceImpl implements GuessServiceLayer {

    @Autowired
    private GuessDao dao;

    @Override
    public int createGame() {
        int newGameId = dao.createGame(createAnswer());
        return newGameId;
    }

    private int createAnswer() {
        List<Integer> randomnums = new ArrayList<>();
        String answer = "";
        for (int i = 0; i < 4; i++) {
            boolean duplicate = true;
            while (duplicate) {
                int nextNum = (int) (Math.random() * 9 + 1);

                if (randomnums.contains(nextNum)) {
                    //Loop again until we have a non duplicate
                } else {
                    randomnums.add(nextNum);
                    answer += Integer.toString(nextNum);
                    duplicate = false;
                }
            }
        }
        return Integer.parseInt(answer);
    }

    @Override
    public Round guessAnswer(int guess, int gameId)
            throws InvalidGameIdException, InvalidGuessFormatException, GameFinishedException {

        validateGameId(gameId);
        validateGameStatus(gameId);
        validateGuess(guess);

        Game game = dao.getGame(gameId);
        int partialMatch = 0;
        int exactMatch = 0;
        String result;

        int[] answerNumbers = String.valueOf(game.getAnswer()).chars().map(Character::getNumericValue).toArray();
        int[] guessNumbers = String.valueOf(guess).chars().map(Character::getNumericValue).toArray();

        for (int i = 0; i < guessNumbers.length; i++) {
            for (int y = 0; y < answerNumbers.length; y++) {

                if (guessNumbers[i] == answerNumbers[y] && i == y) {
                    exactMatch++;
                } else if (guessNumbers[i] == answerNumbers[y] && i != y) {
                    partialMatch++;
                }
            }
        }
        result = "e:" + exactMatch + ":p:" + partialMatch;

        if (exactMatch == 4) {
            dao.updateGameStatus(gameId);
        }
        return dao.guessAnswer(guess, result, gameId);
    }

    @Override
    public Game getGame(int gameId) throws InvalidGameIdException {
        validateGameId(gameId);
        return dao.getGame(gameId);
    }

    @Override
    public List<Game> allGames() {
        return dao.allGames();
    }

    @Override
    public List<Round> allRoundsByGameId(int gameId) throws InvalidGameIdException {
        validateGameId(gameId);

        return dao.allRoundsByGameId(gameId);
    }

    @Override
    public void resetGames() {
        List<Game> allGames = dao.allGames();
        for (Game g : allGames) {
            dao.deleteGame(g.getGameId());
        }
    }

    private boolean validateGameId(int gameId) throws InvalidGameIdException {
        //If game doesnt exist throw error
        Game validGame = dao.getGame(gameId);
        if (validGame == null) {
            throw new InvalidGameIdException("Game with id:" + gameId + " not found");
        }
        return true;
    }

    private boolean validateGameStatus(int gameId) throws GameFinishedException {
        Game validGame = dao.getGame(gameId);
        if (validGame.getStatus()) {
            throw new GameFinishedException("Game " + gameId + " finished. Answer was: " + validGame.getAnswer());
        }
        return true;
    }

    private boolean validateGuess(int guess) throws InvalidGuessFormatException {
        int[] guessNumbers = String.valueOf(guess).chars().map(Character::getNumericValue).toArray();
        
        //If guess has more than 4 numbers then throw exception
        if (guessNumbers.length != 4) {
            throw new InvalidGuessFormatException("Guess can only be 4 digits in length");
        }

        //if guess contains duplicates then throw exception
        for (int i = 0; i < guessNumbers.length; i++) {
            for (int y = 0; y < guessNumbers.length; y++) {

                if (guessNumbers[i] == guessNumbers[y] && i != y) {
                    throw new InvalidGuessFormatException("Guess cannot contain duplicates");
                }
            }
        }
        return true;
    }
}
