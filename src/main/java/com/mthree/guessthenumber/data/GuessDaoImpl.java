package com.mthree.guessthenumber.data;

import com.mthree.guessthenumber.models.Game;
import com.mthree.guessthenumber.models.Round;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author john
 */
@Repository
public class GuessDaoImpl implements GuessDao {

    @Autowired
    private JdbcTemplate jdbc;

    @Override
    @Transactional
    public int createGame(int answer) {
        final String CREATE_GAME = "INSERT INTO games(answer) VALUES (?)";
        jdbc.update(CREATE_GAME, answer);
        return jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
    }

    @Override
    @Transactional
    public Round guessAnswer(int guess, String result, int gameId) {
        final String ADD_ROUND = "INSERT INTO rounds(guess, result, gameId) VALUES (?,?,?)";
        jdbc.update(ADD_ROUND, guess, result, gameId);
        int roundId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        final String GET_ROUND = "SELECT * FROM rounds WHERE gameID = (?) AND round = (?)";

        return jdbc.queryForObject(GET_ROUND, new RoundMapper(), gameId, roundId);
    }

    @Override
    public List<Game> allGames() {
        final String GET_ALL_GAMES = "SELECT * FROM games";
        return jdbc.query(GET_ALL_GAMES, new GameMapper());
    }

    @Override
    public List<Round> allRoundsByGameId(int gameId) {
        final String GET_ALL_ROUNDS_BY_GAMEID = "SELECT * FROM rounds WHERE gameId = (?)";
        return jdbc.query(GET_ALL_ROUNDS_BY_GAMEID, new RoundMapper(), gameId);
    }

    @Override
    public Game getGame(int gameId) {
        try {
            final String GET_GAME = "SELECT * FROM games WHERE gameId = (?)";
            Game retrievedGame = jdbc.queryForObject(GET_GAME, new GameMapper(), gameId);
            return retrievedGame;
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }
    }

    @Override
    public void updateGameStatus(int gameId) {
        final String GET_STATUS = "SELECT game_status FROM games WHERE gameID = ?";
        boolean status = jdbc.queryForObject(GET_STATUS, Boolean.class, gameId);
        final String UPDATE_GAME_STATUS = "UPDATE games SET game_status = ? WHERE gameId = ?";
        jdbc.update(UPDATE_GAME_STATUS, !status, gameId);
    }

    @Override
    public void deleteGame(int gameId) {
        final String DELETE_ROUNDS_BY_GAME = "DELETE FROM rounds WHERE gameId = ?";
        jdbc.update(DELETE_ROUNDS_BY_GAME, gameId);
        final String DELETE_GAME = "DELETE FROM games WHERE gameId = ?";
        jdbc.update(DELETE_GAME, gameId);
    }

    private static final class GameMapper implements RowMapper<Game> {

        @Override
        public Game mapRow(ResultSet rs, int index) throws SQLException {
            Game game = new Game();
            game.setGameId(rs.getInt("gameId"));
            game.setStatus(rs.getBoolean("game_status"));
            game.setAnswer(rs.getInt("answer"));

            return game;
        }
    }

    private static final class RoundMapper implements RowMapper<Round> {

        @Override
        public Round mapRow(ResultSet rs, int index) throws SQLException {
            Round rnd = new Round();
            rnd.setGameId(rs.getInt("gameId"));
            rnd.setRoundNum(rs.getInt("round"));
            rnd.setGuess(rs.getInt("guess"));
            rnd.setResult(rs.getString("result"));
            rnd.setTime(rs.getString("dt"));

            return rnd;
        }
    }
}
