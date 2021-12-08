package com.mthree.guessthenumber.controllers;

import com.mthree.guessthenumber.models.Game;
import com.mthree.guessthenumber.models.Round;
import com.mthree.guessthenumber.service.GameFinishedException;
import com.mthree.guessthenumber.service.GuessServiceLayer;
import com.mthree.guessthenumber.service.InvalidGameIdException;
import com.mthree.guessthenumber.service.InvalidGuessFormatException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author john
 */
@RestController
@RequestMapping("/game/")
public class GuessController {

    @Autowired
    private GuessServiceLayer service;

     @PostMapping("/begin")
    @ResponseStatus(HttpStatus.CREATED)
    public String startGame() {
        return "GameId: " + service.createGame();
    }

    @PostMapping("/guess")
    public ResponseEntity<Round> guessAnswer(int guess, int gameId)
            throws InvalidGuessFormatException, InvalidGameIdException, GameFinishedException {
        Round result = service.guessAnswer(guess, gameId);
        return ResponseEntity.ok(result);
    }

    @GetMapping
    public ResponseEntity<String> getAllGames() {
        List<Game> allGames = service.allGames();
        if (allGames.isEmpty()) {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }
        String gameInfo = "";
        for (Game retrieved : allGames) {
            gameInfo = gameInfo.concat(String.format("gameId: (%d)  Game finished: (%b)\n",
                    retrieved.getGameId(), retrieved.getStatus()));
        }

        return ResponseEntity.ok(gameInfo);
    }

    @GetMapping("/{gameId}")
    public ResponseEntity<String> getGame(@PathVariable int gameId) throws InvalidGameIdException {
        Game retrieved = service.getGame(gameId);
        String gameInfo = String.format("gameId: (%d)  Game finished: (%b)\n",
                retrieved.getGameId(), retrieved.getStatus());
        return ResponseEntity.ok(gameInfo);
    }

    @GetMapping("/rounds/{gameId}")
    public ResponseEntity getAllRoundsByGameId(@PathVariable int gameId) throws InvalidGameIdException {
        List<Round> rounds = service.allRoundsByGameId(gameId);
        if (rounds.isEmpty()) {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }
        return ResponseEntity.ok(rounds);
    }
}
