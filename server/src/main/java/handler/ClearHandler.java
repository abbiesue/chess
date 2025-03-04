package handler;

import com.google.gson.Gson;
import dataaccess.MemoryAuthDAO;
import dataaccess.MemoryGameDAO;
import dataaccess.MemoryUserDAO;
import records.ClearResult;
import server.ResponseException;
import service.ClearService;
import spark.Request;
import spark.Response;

public class ClearHandler {
    MemoryGameDAO gameDAO;
    MemoryAuthDAO authDAO;
    MemoryUserDAO userDAO;
    ClearService clearService;

    public ClearHandler(MemoryGameDAO gameDAO, MemoryAuthDAO authDAO, MemoryUserDAO userDAO) {
        this.gameDAO = gameDAO;
        this.authDAO = authDAO;
        this.userDAO = userDAO;
        clearService = new ClearService(userDAO, gameDAO, authDAO);
    }

    public Object clear(){
        ClearResult result;
        try {
            result = clearService.clear();
        } catch (ResponseException e){
            return e.toJson();
        }
        return new Gson().toJson(result);
    }

}
