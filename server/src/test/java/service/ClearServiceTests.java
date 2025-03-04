package service;

import dataaccess.MemoryAuthDAO;
import dataaccess.MemoryGameDAO;
import dataaccess.MemoryUserDAO;
import model.UserData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import records.*;
import server.ResponseException;

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
        CreateResult gameResult1 = gameService.create(new CreateRequest(userResult2.authToken(), "game1"));
        CreateResult gameResult2 = gameService.create(new CreateRequest(userResult3.authToken(), "game2"));
    }

    @Test
    @DisplayName("clear success")
    public void clearSuccessTest() throws ResponseException {
        clearService.clear();
        Assertions.assertTrue(userDAO.Users.isEmpty());
        Assertions.assertTrue(gameDAO.Games.isEmpty());
        Assertions.assertTrue(authDAO.Auths.isEmpty());
    }
}
