package dataaccess;

import model.AuthData;
import org.junit.jupiter.api.*;
import exceptions.ResponseException;

public class SQLAuthDAOTests {
    private SQLAuthDAO authDAO;
    AuthData existingAuth = new AuthData("authToken", "username");

    @BeforeEach
    void init() throws ResponseException, DataAccessException {
        authDAO = new SQLAuthDAO();
    }

    @AfterEach
    void tearDown() throws ResponseException, DataAccessException {
        authDAO.clear();
    }

    @Test
    @DisplayName("createAuth success")
    public void createAuthSuccess() {
        Assertions.assertDoesNotThrow(()->authDAO.createAuth(existingAuth));
    }

    @Test
    @DisplayName("getAuth success")
    public void getAuthSuccess() {
        Assertions.assertDoesNotThrow(()->authDAO.getAuth(existingAuth.authToken()));
    }

    @Test
    @DisplayName("getAuth failure")
    public void getAuthFailure() throws ResponseException {
        Assertions.assertNull(authDAO.getAuth("nonToken"));
    }

    @Test
    @DisplayName("deleteAuth success")
    public void deleteAuthSuccess() {
        Assertions.assertDoesNotThrow(()->{authDAO.deleteAuth(existingAuth.authToken());});
    }

    @Test
    @DisplayName("clear success")
    public void clearSuccess() {
        Assertions.assertDoesNotThrow(()->{authDAO.clear();});
    }

}
