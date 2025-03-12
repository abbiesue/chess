package handler;

import com.google.gson.Gson;
import dataaccess.*;
import records.ClearResult;
import server.ResponseException;
import service.ClearService;

public class ClearHandler {
    ClearService clearService;

    public ClearHandler(GameDAO gameDAO, AuthDAO authDAO, UserDAO userDAO) {
        clearService = new ClearService(userDAO, gameDAO, authDAO);
    }

    public Object clear(){
        ClearResult result;
        try {
            result = clearService.clear();
        } catch (ResponseException e) {
            return e.toJson();
        }
        return new Gson().toJson(result);
    }

}
