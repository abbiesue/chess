package handler;

import com.google.gson.Gson;
import dataaccess.MemoryAuthDAO;
import dataaccess.MemoryGameDAO;
import dataaccess.MemoryUserDAO;
import model.UserData;
import records.*;
import server.ResponseException;
import service.UserService;
import spark.Request;
import spark.Response;

import java.io.Reader;
import java.lang.reflect.Type;
import java.util.Map;

public class UserHandler {
    public UserService userService;
    public MemoryUserDAO userDAO;
    public MemoryAuthDAO authDAO;

    public UserHandler(MemoryUserDAO userDAO, MemoryAuthDAO authDAO) {
        this.userDAO = userDAO;
        this.authDAO = authDAO;
        userService = new UserService(userDAO, authDAO);
    }

    public Object register(Request req, Response res) throws ResponseException {
        RegisterRequest request = new Gson().fromJson(req.body(), (Type) RegisterRequest.class);
        RegisterResult result;
        try {
            result = userService.register(request);
        } catch (ResponseException e) {
            res.status(e.StatusCode());
            res.type("application/json");
            return e.toJson();
        } catch (Exception e) {
            res.status(500);
            res.type("application/json");
            return new Gson().toJson(Map.of("message", "Error: (description of error)", "status", 500));
        }
        res.status(200);
        res.type("application/json");
        return new Gson().toJson(result);
    }

    public Object login(Request req, Response res) throws ResponseException {
        LoginRequest request = new Gson().fromJson(req.body(), (Type) LoginRequest.class);
        LoginResult result;
        try {
            result = userService.login(request);
        } catch (ResponseException e) {
            res.status(e.StatusCode());
            res.type("application/json");
            return e.toJson();
        } catch (Exception e) {
            res.status(500);
            res.type("application/json");
            return new Gson().toJson(Map.of("message", "Error: (description of error)", "status", 500));
        }
        res.status(200);
        res.type("application/json");
        return new Gson().toJson(result);
    }

//    public Object logout(Request req, Response res) throws ResponseException {
//        LogoutRequest request = new Gson().fromJson(String.valueOf(req.header("authorization"), (Type) LogoutRequest.class);
//        LogoutResult result;
//        try {
//            result = userService.logout(request);
//        } catch (ResponseException e) {
//            res.status(e.StatusCode());
//            res.type("application/json");
//            return e.toJson();
//        }
////        } catch (Exception e) {
////            res.status(500);
////            res.type("application/json");
////            return new Gson().toJson(Map.of("message", "Error: (description of error)", "status", 500));
////        }
//        res.status(200);
//        res.type("application/json");
//        return new Gson().toJson(result);
//    }
}
