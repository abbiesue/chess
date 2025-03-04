package handler;

import com.google.gson.Gson;
import dataaccess.MemoryAuthDAO;
import dataaccess.MemoryGameDAO;
import dataaccess.MemoryUserDAO;
import records.ClearResult;
import server.ResponseException;
import service.ClearService;

public class ClearHandler {
    ClearService clearService;

    public ClearHandler(MemoryGameDAO gameDAO, MemoryAuthDAO authDAO, MemoryUserDAO userDAO) {
        clearService = new ClearService(userDAO, gameDAO, authDAO);
    }

    public Object clear(){
        ClearResult result;
        result = clearService.clear();
        return new Gson().toJson(result);
    }

}
