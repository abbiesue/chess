package dataaccess;

import chess.ChessGame;
import model.GameData;
import model.UserData;
import org.junit.jupiter.api.*;
import server.ResponseException;

import java.util.ArrayList;
import java.util.Collection;

public class SQLGameDAOTests {
    private SQLGameDAO gameDAO;

    @BeforeEach
    void init() throws ResponseException, DataAccessException {
        gameDAO = new SQLGameDAO();
    }

    @AfterEach
    void tearDown() throws ResponseException {
        gameDAO.clear();
    }

    @Test
    @DisplayName("createGame success")
    public void createGameSuccess() {
        Assertions.assertDoesNotThrow(()->gameDAO.createGame("gameName"));
    }

    @Test
    @DisplayName("createGame failure")
    public void createGameFailure() {
        Assertions.assertThrows(ResponseException.class, ()->{gameDAO.createGame(null);});
    }

    @Test
    @DisplayName("getGame success")
    public void getGameSuccess() throws ResponseException {
        GameData game = gameDAO.createGame("gameName");
        Assertions.assertDoesNotThrow(()->gameDAO.getGame(game.gameID()));
    }

    @Test
    @DisplayName("getGame failure")
    public void getGameFailure() throws ResponseException {
        Assertions.assertEquals(null, gameDAO.getGame(3));
    }

    @Test
    @DisplayName("listGames success")
    public void listGamesSuccess() throws ResponseException {
        gameDAO.createGame("gameName");
        Assertions.assertDoesNotThrow(()->gameDAO.listGames());
        Assertions.assertNotNull(gameDAO.listGames());
    }

    @Test
    @DisplayName("listGames failure")
    public void listGamesFailure() throws ResponseException {
        Collection<GameData> emptyList = new ArrayList<>();
        Assertions.assertEquals(emptyList, gameDAO.listGames());
    }

    @Test
    @DisplayName("updateGame success")
    public void updateGameSuccess() throws ResponseException {
        GameData game = gameDAO.createGame("gameName");
        Assertions.assertDoesNotThrow(()->gameDAO.updateGame(
                game.gameID(), ChessGame.TeamColor.WHITE, "username"));
    }

    @Test
    @DisplayName("updateGame failure")
    public void updateGameFailure() {
        Assertions.assertThrows(NullPointerException.class, ()->
        {gameDAO.updateGame(3, ChessGame.TeamColor.WHITE, "username");});
    }

    @Test
    @DisplayName("deleteGame success")
    public void deleteGameSuccess() throws ResponseException {
        GameData game = gameDAO.createGame("gameName");
        Assertions.assertDoesNotThrow(()->gameDAO.deleteGame(game.gameID()));
    }

    @Test
    @DisplayName("clear success")
    public void clearSuccess() throws ResponseException {
        GameData game = gameDAO.createGame("gameName");
        Assertions.assertDoesNotThrow(()->{gameDAO.clear();});
    }


}
