package client;

import org.junit.jupiter.api.*;
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
    public void sampleTest() {
        Assertions.assertTrue(true);
    }

}
