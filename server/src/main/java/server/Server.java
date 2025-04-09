package server;

import dataaccess.*;
import handler.*;
import server.websocket.WebSocketHandler;
import spark.*;

public class Server {
    UserHandler userHandler;
    GameHandler gameHandler;
    ClearHandler clearHandler;

    UserDAO userDAO;
    AuthDAO authDAO;
    GameDAO gameDAO;

    private final WebSocketHandler webSocketHandler;

    public Server() {
        try {
            userDAO = new SQLUserDAO();
            authDAO = new SQLAuthDAO();
            gameDAO = new SQLGameDAO();
        } catch (ResponseException | DataAccessException e) {
            e.printStackTrace();
        }

        userHandler = new UserHandler(userDAO, authDAO);
        gameHandler = new GameHandler(gameDAO, authDAO);
        clearHandler = new ClearHandler(gameDAO, authDAO, userDAO);
        webSocketHandler = new WebSocketHandler((SQLAuthDAO) authDAO, (SQLGameDAO) gameDAO, (SQLUserDAO) userDAO);
    }

    public int run(int desiredPort) {
        Spark.port(desiredPort);

        Spark.staticFiles.location("web");

        Spark.webSocket("/ws", webSocketHandler);

        // Register your endpoints and handle exceptions here.
        Spark.post("/user", (request, response) -> {
           return userHandler.register(request, response);
        });
        Spark.post("/session", (request, response) -> {
            return userHandler.login(request, response);
        });
        Spark.delete("/session", (request, response) -> {
            return userHandler.logout(request, response);
        });
        Spark.delete("/db", (request, response) -> {
            return clearHandler.clear();
        });
        Spark.get("/game", (request, response) -> {
           return gameHandler.listGames(request, response);
        });
        Spark.post("/game", (request, response) -> {
            return gameHandler.createGame(request, response);
        });
        Spark.put("/game", (request, response) -> {
           return gameHandler.joinGame(request, response);
        });

        Spark.awaitInitialization();
        return Spark.port();
    }

    public void stop() {
        Spark.stop();
        Spark.awaitStop();
    }
}
