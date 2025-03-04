package server;

import dataaccess.MemoryAuthDAO;
import dataaccess.MemoryGameDAO;
import dataaccess.MemoryUserDAO;
import handler.ClearHandler;
import handler.GameHandler;
import handler.UserHandler;
import spark.*;

public class Server {
    UserHandler userHandler;
    GameHandler gameHandler;
    ClearHandler clearHandler;

    MemoryUserDAO userDAO;
    MemoryAuthDAO authDAO;
    MemoryGameDAO gameDAO;

    public Server() {
        userDAO = new MemoryUserDAO();
        authDAO = new MemoryAuthDAO();
        gameDAO = new MemoryGameDAO();

        userHandler = new UserHandler(userDAO, authDAO);
        gameHandler = new GameHandler(gameDAO, authDAO);
        clearHandler = new ClearHandler();
    }

    public int run(int desiredPort) {
        Spark.port(desiredPort);

        Spark.staticFiles.location("web");

        // Register your endpoints and handle exceptions here.
        Spark.post("/user", (request, response) -> {
           return userHandler.register(request, response);
        });
        Spark.post("/session", (request, response) -> {
            return userHandler.login(request, response);
        });
//        Spark.delete("/session", (request, response) -> {
//            return userHandler.logout(request, response);
//        });


//        Spark.delete("/db", (request, response) -> {
//            return clearHandler.clear(request,response);
//        });

        //This line initializes the server and can be removed once you have a functioning endpoint 
        Spark.init();

        Spark.awaitInitialization();
        return Spark.port();
    }

    public void stop() {
        Spark.stop();
        Spark.awaitStop();
    }
}
