package dataaccess;

import model.UserData;
import org.junit.jupiter.api.*;
import records.RegisterRequest;
import Exceptions.ResponseException;
import service.UserService;

public class SQLUserDAOTests {
    private SQLUserDAO userDAO;
    private SQLAuthDAO authDAO;
    private UserService userService;
    UserData existingUser = new UserData("existingUser", "existingPassword", "existingEmail");

    @BeforeEach
    void init() throws ResponseException, DataAccessException {
        userDAO = new SQLUserDAO();
        authDAO = new SQLAuthDAO();
        userService = new UserService(userDAO, authDAO);
    }

    @AfterEach
    void tearDown() throws ResponseException, DataAccessException {
        userDAO.clear();
        authDAO.clear();
    }

    @Test
    @DisplayName("createUser success")
    public void createUserSuccess() throws ResponseException, DataAccessException {
        userDAO = new SQLUserDAO();
        Assertions.assertDoesNotThrow(()->{userDAO.createUser(existingUser);});
    }

    @Test
    @DisplayName("createUser failure")
    public void createUserFailure() throws ResponseException {
        userDAO.createUser(existingUser);
        Assertions.assertThrows(ResponseException.class, ()->{userService.register(
                new RegisterRequest(existingUser.username(), existingUser.password(), existingUser.email()));});
    }

    @Test
    @DisplayName("getUser success")
    public void getUserSuccess() throws ResponseException {
        userDAO.createUser(existingUser);
        Assertions.assertDoesNotThrow(()->{userDAO.getUser(existingUser.username());});
    }

    @Test
    @DisplayName("getUser failure")
    public void getUserFailure() throws ResponseException, DataAccessException {
        userDAO.clear();
        Assertions.assertEquals(null, userDAO.getUser(existingUser.username()));
    }

    @Test
    @DisplayName("clear success")
    public void clearSuccess() {
        Assertions.assertDoesNotThrow(()-> {userDAO.clear();});
    }


}
