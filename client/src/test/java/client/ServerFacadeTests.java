package client;

import model.UserData;
import org.junit.jupiter.api.*;
import records.LoginRequest;
import records.LoginResult;
import records.RegisterRequest;
import records.RegisterResult;
import server.ResponseException;
import server.Server;
import server.ServerFacade;


public class ServerFacadeTests {
    private static final String DOMAIN_NAME = "localhost";

    private static Server server;
    private static ServerFacade facade;
    private static String url;
    private static int port;

    private UserData newUser = new UserData("newUser", "newPass", "new@email");
    private UserData oldUser = new UserData("oldUser", "oldPass", "old@email");

    @BeforeAll
    public static void init() {
        server = new Server();
        port = server.run(0);
        url = "http://" + DOMAIN_NAME + ":"  + port;
        System.out.println("Started test HTTP server on " + port);
        facade = new ServerFacade(url);
    }

    @AfterAll
    static void stopServer() {
        server.stop();
    }

    @Test
    @DisplayName("check init")
    public void checkInit() {
        Assertions.assertEquals("http://localhost:" + port, url);
    }

    @Test
    @DisplayName("clear success")
    public void clearSuccess() {
        Assertions.assertDoesNotThrow(()->facade.clear());
    }

    @Nested
    @DisplayName("register tests")
    class RegisterTests {
        RegisterRequest newReq = new RegisterRequest(newUser.username(), newUser.password(), newUser.email());
        RegisterRequest badReq = new RegisterRequest(null, null, null);

        @BeforeEach
        public void init() throws ResponseException {
            facade.clear();
        }

        @Test
        @DisplayName("register runs")
        public void registerRuns() {
            Assertions.assertDoesNotThrow(()->facade.register(newReq));
        }

        @Test
        @DisplayName("register output check")
        public void registerOutputCheck() throws ResponseException {
            Assertions.assertEquals(facade.register(newReq).getClass(), RegisterResult.class);
        }

        @Test
        @DisplayName("register success")
        public void registerSuccess() throws ResponseException {
            RegisterResult result = facade.register(newReq);
            Assertions.assertEquals(newReq.username(), result.username());
            Assertions.assertNotNull(result.authToken());
        }

        @Test
        @DisplayName("register failure - already taken")
        public void registerAlreadyTaken() throws ResponseException {
            facade.register(newReq);
            try {
                facade.register(newReq);
            } catch (ResponseException e) {
                Assertions.assertEquals(403, e.statusCode());
            }
        }

        @Test
        @DisplayName("register failure - bad request")
        public void registerBadRequest() {
            try {
                facade.register(badReq);
            } catch (ResponseException e) {
                Assertions.assertEquals(400, e.statusCode());
            }
        }
    }

    @Nested
    @DisplayName("login tests")
    class LoginTests {
        LoginRequest oldReq = new LoginRequest(oldUser.username(), oldUser.password());
        LoginRequest newReq = new LoginRequest(newUser.username(), newUser.password());

        @BeforeEach
        public void init() throws ResponseException {
            facade.clear();
            facade.register(new RegisterRequest(oldUser.username(), oldUser.password(), oldUser.email()));
        }

        @Test
        @DisplayName("login success")
        public void loginSuccess() throws ResponseException {
            Assertions.assertDoesNotThrow(()-> facade.login(oldReq));
            Assertions.assertEquals(facade.login(oldReq).getClass(), LoginResult.class);
            LoginResult result = facade.login(oldReq);
            Assertions.assertEquals(oldUser.username(), result.username());
            Assertions.assertNotNull(result.authToken());
        }

        @Test
        @DisplayName("login success - unique tokens")
        public void loginUniqueTokens() throws ResponseException {
            LoginResult result1 = facade.login(oldReq);
            LoginResult result2 = facade.login(oldReq);
            Assertions.assertNotEquals(result1.authToken(), result2.authToken());
        }

        @Test
        @DisplayName("login failure - nonexisting user")
        public void loginNonexisting() {
            try {
                facade.login(newReq);
            } catch (ResponseException e) {
                Assertions.assertEquals(401, e.statusCode());
            }
        }

        @Test
        @DisplayName("login failure - wrong password")
        public void loginWrongPassword() {
            try {
                facade.login(new LoginRequest(oldUser.username(), "wrongPass"));
            } catch (ResponseException e) {
                Assertions.assertEquals(401, e.statusCode());
            }
        }

        @Test
        @DisplayName("login failure - bad request")
        public void loginBadRequest() {
            try {
                facade.login(new LoginRequest(null, null));
            } catch (ResponseException e) {
                Assertions.assertEquals(401, e.statusCode());
            }
        }
    }

    @Nested
    @DisplayName("logout tests")
    class LogoutTests {

    }

}
