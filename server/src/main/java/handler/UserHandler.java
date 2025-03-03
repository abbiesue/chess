package handler;

import com.google.gson.Gson;
import dataaccess.exceptions.RegisterFailure;
import model.UserData;
import server.ResponseException;
import service.UserService;
import spark.Request;
import spark.Response;

import java.lang.reflect.Type;

public class UserHandler {
    public Object register(Request req, Response res) throws ResponseException {
        UserService.RegisterRequest request = new Gson().fromJson(req.body(), (Type) UserData.class);
        UserService.RegisterResult result;
        try {
            result = UserService.register(request);
        } catch (ResponseException e) {
            return e.toJson();
        }
        return new Gson().toJson(result);
    }
}
