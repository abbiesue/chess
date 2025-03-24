package client;

import org.junit.jupiter.api.*;
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
        RegisterRequest newReq = new RegisterRequest("newUser", "newPass", "new@email");
        RegisterRequest badReq = new RegisterRequest(null, null, null);
        //FINISH TESTS ONCE CLEAR IS IMPLEMENTED
        @BeforeEach
        public void init() throws ResponseException {
            facade.clear();
        }

        @Test
        @DisplayName("register runs")
        public void registerRuns() throws ResponseException {
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

    @Test
    public void sampleTest() {
        Assertions.assertTrue(true);
    }

}
