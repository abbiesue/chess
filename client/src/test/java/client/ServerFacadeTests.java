package client;

import model.UserData;
import org.junit.jupiter.api.*;
import records.*;
import Exceptions.ResponseException;
import server.Server;
import server.ServerFacade;


public class ServerFacadeTests {
    private static final String DOMAIN_NAME = "localhost";

    private static Server server;
    private static ServerFacade facade;
    private static String url;
    private static int port;

    private final UserData newUser = new UserData("newUser", "newPass", "new@email");
    private final UserData oldUser = new UserData("oldUser", "oldPass", "old@email");

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
    void checkInit() {
        Assertions.assertEquals("http://localhost:" + port, url);
    }

    @Test
    @DisplayName("clear success")
    void clearSuccess() {
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
        void registerRuns() {
            Assertions.assertDoesNotThrow(()->facade.register(newReq));
        }

        @Test
        @DisplayName("register output check")
        void registerOutputCheck() throws ResponseException {
            Assertions.assertEquals(facade.register(newReq).getClass(), RegisterResult.class);
        }

        @Test
        @DisplayName("register success")
        void registerSuccess() throws ResponseException {
            RegisterResult result = facade.register(newReq);
            Assertions.assertEquals(newReq.username(), result.username());
            Assertions.assertNotNull(result.authToken());
        }

        @Test
        @DisplayName("register failure - already taken")
        void registerAlreadyTaken() throws ResponseException {
            facade.register(newReq);
            try {
                facade.register(newReq);
            } catch (ResponseException e) {
                Assertions.assertEquals(403, e.statusCode());
            }
        }

        @Test
        @DisplayName("register failure - bad request")
        void registerBadRequest() {
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
        void loginSuccess() throws ResponseException {
            Assertions.assertDoesNotThrow(()-> facade.login(oldReq));
            Assertions.assertEquals(facade.login(oldReq).getClass(), LoginResult.class);
            LoginResult result = facade.login(oldReq);
            Assertions.assertEquals(oldUser.username(), result.username());
            Assertions.assertNotNull(result.authToken());
        }

        @Test
        @DisplayName("login success - unique tokens")
        void loginUniqueTokens() throws ResponseException {
            LoginResult result1 = facade.login(oldReq);
            LoginResult result2 = facade.login(oldReq);
            Assertions.assertNotEquals(result1.authToken(), result2.authToken());
        }

        @Test
        @DisplayName("login failure - nonexisting user")
        void loginNonexisting() {
            try {
                facade.login(newReq);
            } catch (ResponseException e) {
                Assertions.assertEquals(401, e.statusCode());
            }
        }

        @Test
        @DisplayName("login failure - wrong password")
        void loginWrongPassword() {
            try {
                facade.login(new LoginRequest(oldUser.username(), "wrongPass"));
            } catch (ResponseException e) {
                Assertions.assertEquals(401, e.statusCode());
            }
        }

        @Test
        @DisplayName("login failure - bad request")
        void loginBadRequest() {
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
        String authToken;

        @BeforeEach
        public void init() throws ResponseException {
            facade.clear();
            RegisterResult result = facade.register(new RegisterRequest(oldUser.username(), oldUser.password(), oldUser.email()));
            authToken = result.authToken();
        }

        @Test
        @DisplayName("logout success")
        void logoutSuccess() {
            Assertions.assertDoesNotThrow(()->facade.logout(new LogoutRequest(authToken)));
            Assertions.assertThrows(ResponseException.class, ()->facade.logout(new LogoutRequest(authToken)));
        }

        @Test
        @DisplayName("logout failure - bad auth")
        void logoutBadAuth() {
            try {
                facade.logout(new LogoutRequest("badAuth"));
            } catch (ResponseException e) {
                Assertions.assertEquals(401, e.statusCode());
            }
        }

        @Test
        @DisplayName("login failure - bad request")
        void logoutBadRequest() {
            try {
                facade.logout(new LogoutRequest(null));
            } catch (ResponseException e) {
                Assertions.assertEquals(401, e.statusCode());
            }
        }

    }

    @Nested
    @DisplayName("create tests")
    class CreateTests {
        CreateRequest goodReq;
        CreateRequest badAuth = new CreateRequest("badAuth", "badGame");
        CreateRequest badReq;
        String authToken;

        @BeforeEach
        void init() throws ResponseException {
            facade.clear();
            RegisterResult result = facade.register(new RegisterRequest(oldUser.username(), oldUser.password(), oldUser.email()));
            authToken = result.authToken();
            goodReq = new CreateRequest(authToken, "goodGame");
            badReq = new CreateRequest(authToken, "badGame");
        }

        @Test
        @DisplayName("create success")
        void createSuccess() {
            Assertions.assertDoesNotThrow(()->facade.create(goodReq));
        }

        @Test
        @DisplayName("create failure - bad auth")
        void createBadAuth() {
            try {
                facade.create(badAuth);
            } catch (ResponseException e) {
                Assertions.assertEquals(401, e.statusCode());
            }
        }

        @Test
        @DisplayName("create failure - bad req")
        void createBadReq() {
            try {
                facade.create(badReq);
            } catch (ResponseException e) {
                Assertions.assertEquals(400, e.statusCode());
            }
        }
    }

    @Nested
    @DisplayName("join tests")
    class JoinTests {
        String authToken;
        int gameID;

        @BeforeEach
        public void init() throws ResponseException {
            facade.clear();
            RegisterResult result = facade.register(
                    new RegisterRequest(oldUser.username(), oldUser.password(), oldUser.email()));
            authToken = result.authToken();
            CreateResult gameResult = facade.create(new CreateRequest(authToken, "goodGame"));
            gameID = gameResult.gameID();
        }

        @Test
        @DisplayName("join success")
        void joinSuccess() {
            Assertions.assertDoesNotThrow(()->facade.join(new JoinRequest(authToken, "WHITE", gameID)));
        }

        @Test
        @DisplayName("join failure - bad auth")
        void joinBadAuth() {
            try {
                facade.join(new JoinRequest("badauth", "WHITE", gameID));
            } catch (ResponseException e) {
                Assertions.assertEquals(401, e.statusCode());
            }
        }

        @Test
        @DisplayName("join failure - bad team color")
        void joinBadTeamColor() {
            try {
                facade.join(new JoinRequest(authToken, "GREEN", gameID));
            } catch (ResponseException e) {
                Assertions.assertEquals(400, e.statusCode());
            }
        }

        @Test
        @DisplayName("join failure - bad gameID")
        void joinBadGameID() {
            try {
                facade.join(new JoinRequest(authToken, "WHITE", null));
            } catch (ResponseException e) {
                Assertions.assertEquals(400, e.statusCode());
            }
        }

        @Test
        @DisplayName("join failure - already taken")
        void joinAlreadyTaken() throws ResponseException {
            facade.join(new JoinRequest(authToken, "WHITE", gameID));
            try {
                facade.join(new JoinRequest(authToken, "WHITE", gameID));
            } catch (ResponseException e) {
                Assertions.assertEquals(403, e.statusCode());
            }
        }
    }

    @Nested
    @DisplayName("list tests")
    class ListTests {
        String authToken;
        int gameID1;
        int gameID2;

        @BeforeEach
        public void init() throws ResponseException {
            facade.clear();
            RegisterResult regResult = facade.register(
                    new RegisterRequest(oldUser.username(), oldUser.password(), oldUser.email()));
            authToken = regResult.authToken();
            CreateResult gameResult = facade.create(new CreateRequest(authToken, "game1"));
            gameID1 = gameResult.gameID();
            gameResult = facade.create(new CreateRequest(authToken, "game2"));
            gameID2 = gameResult.gameID();
            facade.join(new JoinRequest(authToken, "WHITE", gameID1));
        }

        @Test
        @DisplayName("list success")
        void listSuccess() {
            Assertions.assertDoesNotThrow(()-> facade.list(new ListRequest(authToken)));
        }

        @Test
        @DisplayName("list failure - bad auth")
        void listBadAuth() {
            try {
                facade.list(new ListRequest(null));
            } catch (ResponseException e) {
                Assertions.assertEquals(401, e.statusCode());
            }
        }
    }

}
