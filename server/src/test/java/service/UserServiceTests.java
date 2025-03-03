package service;

import dataaccess.exceptions.RegisterFailure;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import server.ResponseException;

public class UserServiceTests {
    //test register
    //if user does not exist
    //if the request is not in the right format
    //if the request is missing information
    //if user already exists
    @Test
    @DisplayName("register success test")
    public void registerSuccess() throws ResponseException {
        UserService.RegisterRequest request = new UserService.RegisterRequest("freeUsername",
                "password1234", "totallyreal@real.com");
        UserService.RegisterResult result = UserService.register(request);
        Assertions.assertEquals(request.username(), result.username(), "username of request and result did not match");
        Assertions.assertNotNull(result.authToken(), "result had a null authToken");
    }
    //test login
    //test logout
}
