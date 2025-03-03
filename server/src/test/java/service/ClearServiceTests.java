package service;

import model.UserData;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import records.RegisterRequest;
import records.RegisterResult;
import server.ResponseException;

public class ClearServiceTests {
    @Test
    @DisplayName("clear success")
    public void clearSuccessTest() throws ResponseException {
        UserService userService = new UserService();

        RegisterRequest testUser = new RegisterRequest("testUsername", "Pass1234", "totallyreal@email.com");
        RegisterResult result = userService.register(testUser);

    }
}
