package service;

import dataaccess.MemoryAuthDAO;
import dataaccess.MemoryUserDAO;
import model.UserData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import records.*;
import exceptions.ResponseException;

public class UserServiceTests {
    UserService userService;
    MemoryAuthDAO authDAO;
    MemoryUserDAO userDAO;
    UserData existingUser = new UserData("existingUser", "existingPassword", "existingEmail");
    UserData newUser = new UserData("newUser", "newPassword", "newEmail");

    @BeforeEach
    public void init() throws ResponseException {
        authDAO = new MemoryAuthDAO();
        userDAO = new MemoryUserDAO();
        userService = new UserService(userDAO, authDAO);
        //add an existing user
        RegisterResult registerExisting = userService.register(new RegisterRequest(existingUser.username(),
                existingUser.password(), existingUser.email()));
        userService.logout(new LogoutRequest(registerExisting.authToken()));
    }

    @Test
    @DisplayName("register success test")
    public void registerSuccess() throws ResponseException {
        RegisterRequest request = new RegisterRequest(newUser.username(), newUser.password(), newUser.email());
        RegisterResult result = userService.register(request);
        Assertions.assertEquals(request.username(), result.username(), "username of request and result did not match");
        Assertions.assertNotNull(result.authToken(), "result had a null authToken");
    }

    @Test
    @DisplayName("register existing user")
    public void registerExistingUser() {
        RegisterRequest copyRequest = new RegisterRequest(existingUser.username(), existingUser.password(),
                existingUser.email());
        Assertions.assertThrows(ResponseException.class, ()-> {userService.register(copyRequest);});
    }

    @Test
    @DisplayName("register missing information")
    public void registerMissingInfo() {
        RegisterRequest noUsername = new RegisterRequest(null, newUser.password(), newUser.email());
        Assertions.assertThrows(ResponseException.class, ()-> {userService.register(noUsername);});

        RegisterRequest noPassword = new RegisterRequest(newUser.username(), null, newUser.email());
        Assertions.assertThrows(ResponseException.class, ()-> {userService.register(noPassword);});

        RegisterRequest noEmail = new RegisterRequest(newUser.username(), newUser.password(), null);
        Assertions.assertThrows(ResponseException.class, ()-> {userService.register(noEmail);});
    }

    @Test
    @DisplayName("login nonexisting user")
    public void loginNonexistingUser() {
        LoginRequest newLogin = new LoginRequest(newUser.username(), newUser.password());
        Assertions.assertThrows(ResponseException.class, ()-> {userService.login(newLogin);});
    }

    @Test
    @DisplayName("logout unauthorized")
    public void logOutUnauthorized() {
        LogoutRequest logoutRequest = new LogoutRequest("fakeAuth");
        Assertions.assertThrows(ResponseException.class, ()->userService.logout(logoutRequest));
    }
    //test logout
}
