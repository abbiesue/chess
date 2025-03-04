package service;

import chess.ChessGame;
import dataaccess.MemoryAuthDAO;
import dataaccess.MemoryGameDAO;
import dataaccess.MemoryUserDAO;
import model.AuthData;
import model.GameData;
import model.UserData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import records.*;
import server.ResponseException;

public class GameServiceTests {
    MemoryGameDAO gameDAO = new MemoryGameDAO();
    MemoryAuthDAO authDAO = new MemoryAuthDAO();
    MemoryUserDAO userDAO = new MemoryUserDAO();
    public GameService gameService;
    public UserService userService;
    UserData existingUser = new UserData("existingUser", "existingPassword", "existing@email.com");
    String authToken;

    @BeforeEach
    public void init() throws ResponseException {
        gameService = new GameService(gameDAO, authDAO);
        userService = new UserService(userDAO, authDAO);

        RegisterResult existingResult = userService.register(
                new RegisterRequest(existingUser.username(), existingUser.password(), existingUser.email()));
        authToken = existingResult.authToken();
        gameService.create(new CreateRequest(authToken, "existingGame"));
    }

    @Test
    @DisplayName("createGame Success")
    public void createGameSuccess() throws ResponseException {
        CreateResult newGameResult = gameService.create(new CreateRequest(authToken, "newGame"));
        Assertions.assertNotNull(newGameResult.gameID(), "returned gameID is null");
    }

    @Test
    @DisplayName("createGame unauthorized")
    public void createGameUnauthorized() {
        String badAuth = "badAuth";
        Assertions.assertThrows(ResponseException.class, ()-> {gameService.create(new CreateRequest(badAuth, "newGame"));});
    }

    @Test
    @DisplayName("createGame null name")
    public void createGameNullName() {
        Assertions.assertThrows(ResponseException.class, ()-> {gameService.create(new CreateRequest(authToken, null));});
    }
    @Test
    @DisplayName("listGames success")
    public void listGamesSuccess() {
        Assertions.assertDoesNotThrow(()->{gameService.list(new ListRequest(authToken));});
    }
    @Test
    @DisplayName("listGames unauthorized")
    public void listGamesUnauthorized() {
        String badAuth = "badAuth";
        Assertions.assertThrows(ResponseException.class, ()->{gameService.list(new ListRequest(badAuth));});
    }
    //joinGameSuccess
    @Test
    @DisplayName("joinGame success")
    public void joinGameSuccess() {
        Assertions.assertDoesNotThrow(()->{gameService.join(new JoinRequest(authToken, "WHITE", 1));});
    }
    //joinGameBadRequest
    @Test
    @DisplayName("joinGame bad request")
    public void joinGameBadRequest() {
        Assertions.assertThrows(ResponseException.class, ()->{gameService.join(new JoinRequest(authToken, "GREEN", 1));});
        Assertions.assertThrows(ResponseException.class, ()->{gameService.join(new JoinRequest(authToken, "WHITE", null));});
        Assertions.assertThrows(ResponseException.class, ()->{gameService.join(new JoinRequest(authToken, null, 1));});
        Assertions.assertThrows(ResponseException.class, ()->{gameService.join(new JoinRequest(authToken, "WHITE", 2));});
    }
    //joinGameUnauthorized
    @Test
    @DisplayName("joinGame unauthorized")
    public void joinGameUnauthorized() {
        String badAuth = "badAuth";
        Assertions.assertThrows(ResponseException.class, ()->{gameService.join(new JoinRequest(badAuth, "WHITE", 1));});
    }
    //joinGameColorTaken
    @Test
    @DisplayName("joinGame white taken")
    public void joinGameWhiteTaken() throws ResponseException {
        gameService.join(new JoinRequest(authToken, "WHITE", 1));
        Assertions.assertThrows(ResponseException.class, ()->{gameService.join(new JoinRequest(authToken, "WHITE", 1));});
    }

    @Test
    @DisplayName("joinGame black taken")
    public void joinGameBlackTaken() throws ResponseException {
        gameService.join(new JoinRequest(authToken, "BLACK", 1));
        Assertions.assertThrows(ResponseException.class, ()->{gameService.join(new JoinRequest(authToken, "BLACK", 1));});
    }

}
