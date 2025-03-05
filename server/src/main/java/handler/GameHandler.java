package handler;

import com.google.gson.Gson;
import dataaccess.MemoryAuthDAO;
import dataaccess.MemoryGameDAO;
import records.*;
import server.ResponseException;
import service.GameService;
import spark.Request;
import spark.Response;

import java.lang.reflect.Type;

public class GameHandler {
    public GameService gameService;

    public GameHandler(MemoryGameDAO gameDAO, MemoryAuthDAO authDAO) {
        gameService = new GameService(gameDAO, authDAO);;
    }

    public Object listGames(Request req, Response res) {
        ListRequest request = new ListRequest(req.headers("Authorization"));
        ListResult result;
        try {
            result = gameService.list(request);
        } catch (ResponseException e) {
            res.status(e.statusCode());
            res.type("application/json");
            return e.toJson();
        }
        res.status(200);
        res.type("application/json");
        return new Gson().toJson(result);
    }

    public Object createGame(Request req, Response res) {
        String authToken = req.headers("Authorization");
        CreateRequestPrimative primative = new Gson().fromJson(req.body(), (Type) CreateRequestPrimative.class);
        CreateRequest request = new CreateRequest(authToken, primative.gameName());
        CreateResult result;
        try {
            result = gameService.create(request);
        } catch (ResponseException e) {
            res.status(e.statusCode());
            res.type("application/json");
            return e.toJson();
        }
        res.status(200);
        res.type("application/json");
        return new Gson().toJson(result);
    }

    public Object joinGame(Request req, Response res) {
        String authToken = req.headers("Authorization");
        JoinRequestPrimative primative = new Gson().fromJson(req.body(), (Type) JoinRequestPrimative.class);
        JoinRequest request = new JoinRequest(authToken, primative.playerColor(), primative.gameID());
        JoinResult result;
        try {
            result = gameService.join(request);
        } catch (ResponseException e) {
            res.status(e.statusCode());
            res.type("application/json");
            return e.toJson();
        }
        res.status(200);
        res.type("application/json");
        return new Gson().toJson(result);
    }
}
