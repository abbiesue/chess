package handler;

import com.google.gson.Gson;
import model.UserData;
import records.RegisterRequest;
import records.RegisterResult;
import server.ResponseException;
import service.UserService;
import spark.Request;
import spark.Response;

import java.lang.reflect.Type;

public class UserHandler {
    public UserService userService;

    public UserHandler() {
        userService = new UserService();
    }

    public Object register(Request req, Response res) throws ResponseException {
        RegisterRequest request = new Gson().fromJson(req.body(), (Type) RegisterRequest.class);
        RegisterResult result;
        try {
            result = userService.register(request);
        } catch (ResponseException e) {
            res.type("application/json");
            return e.toJson();
        }
        return new Gson().toJson(result);
    }
}
