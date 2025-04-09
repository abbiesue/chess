package service;

import dataaccess.DataAccessException;
import dataaccess.MemoryAuthDAO;
import dataaccess.MemoryGameDAO;
import dataaccess.MemoryUserDAO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import records.*;
import exceptions.ResponseException;

public class ClearServiceTests {
    MemoryGameDAO gameDAO = new MemoryGameDAO();
    MemoryUserDAO userDAO = new MemoryUserDAO();
    MemoryAuthDAO authDAO = new MemoryAuthDAO();
    GameService gameService;
    UserService userService;
    ClearService clearService;

    @BeforeEach
    public void init() throws ResponseException {
        gameService = new GameService(gameDAO, authDAO);
        userService = new UserService(userDAO, authDAO);
        clearService = new ClearService(userDAO, gameDAO, authDAO);

        //populate users
        RegisterResult userResult1 = userService.register(new RegisterRequest("user1", "password1", "email1"));
        userService.logout(new LogoutRequest(userResult1.authToken()));
        RegisterResult userResult2 = userService.register(new RegisterRequest("user2", "password2", "email2"));
        RegisterResult userResult3 = userService.register(new RegisterRequest("user3", "password3", "email3"));

        //populate games
        gameService.create(new CreateRequest(userResult2.authToken(), "game1"));
        gameService.create(new CreateRequest(userResult3.authToken(), "game2"));
    }

    @Test
    @DisplayName("clear success")
    public void clearSuccessTest() throws ResponseException, DataAccessException {
        clearService.clear();
        Assertions.assertTrue(userDAO.users.isEmpty());
        Assertions.assertTrue(gameDAO.games.isEmpty());
        Assertions.assertTrue(authDAO.auths.isEmpty());
    }
}
