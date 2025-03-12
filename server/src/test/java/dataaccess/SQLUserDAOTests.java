package dataaccess;

import model.UserData;
import org.junit.jupiter.api.*;
import server.ResponseException;

public class SQLUserDAOTests {
    private SQLUserDAO testDAO;
    private UserData existingUser = new UserData("existingUser", "existingPassword", "existingEmail");
    private UserData newUser = new UserData("newUser", "newPassword", "newEmail");

    @BeforeEach
    public void init() {
        try {
            testDAO = new SQLUserDAO();
            testDAO.createUser(existingUser);
        } catch (ResponseException | DataAccessException e) {
            e.printStackTrace();
        }
    }

    @AfterEach
    public void destruct() {
        try {
            testDAO.clear();
        } catch (ResponseException e) {
            e.printStackTrace();
        }
    }

    @Test
    @DisplayName("clear success")
    void clearSuccess() {
        Assertions.assertDoesNotThrow(()->{testDAO.clear();});
    }


}
