package com.mthree.guessthenumber.models;

import java.util.List;

/**
 *
 * @author john
 */
public class Game {

    private int gameId;

    private int answer;

    private boolean status;

    public Game() {

    }

    public int getGameId() {
        return gameId;
    }

    public void setGameId(int gameId) {
        this.gameId = gameId;
    }

    public int getAnswer() {
        return answer;
    }

    public void setAnswer(int answer) {
        this.answer = answer;
    }

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
