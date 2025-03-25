package handler;

import com.google.gson.Gson;
import dataaccess.*;
import records.*;
import server.ResponseException;
import service.UserService;
import spark.Request;
import spark.Response;
import java.lang.reflect.Type;

public class UserHandler {
    public UserService userService;

    public UserHandler(UserDAO userDAO, AuthDAO authDAO) {
        userService = new UserService(userDAO, authDAO);
    }

    public Object register(Request req, Response res) {
        RegisterRequest request = new Gson().fromJson(req.body(), (Type) RegisterRequest.class);
        RegisterResult result;
        try {
            result = userService.register(request);
        } catch (ResponseException e) {
            res.status(e.statusCode());
            res.type("application/json");
            return e.toJson();
        }
        res.status(200);
        res.type("application/json");
        return new Gson().toJson(result);
    }

    public Object login(Request req, Response res) {
        LoginRequest request = new Gson().fromJson(req.body(), (Type) LoginRequest.class);
        LoginResult result;
        try {
            result = userService.login(request);
        } catch (ResponseException e) {
            res.status(e.statusCode());
            res.type("application/json");
            return e.toJson();
        }
        res.status(200);
        res.type("application/json");
        return new Gson().toJson(result);
    }

    public Object logout(Request req, Response res) {
        System.out.println(req.toString());
        LogoutRequest request = new LogoutRequest(req.headers("Authorization"));
        LogoutResult result;
        try {
            result = userService.logout(request);
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
