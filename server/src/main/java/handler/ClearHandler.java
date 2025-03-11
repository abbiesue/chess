package handler;

import com.google.gson.Gson;
import dataaccess.*;
import records.ClearResult;
import service.ClearService;

public class ClearHandler {
    ClearService clearService;

    public ClearHandler(GameDAO gameDAO, AuthDAO authDAO, UserDAO userDAO) {
        clearService = new ClearService(userDAO, gameDAO, authDAO);
    }

    public Object clear(){
        ClearResult result;
        result = clearService.clear();
        return new Gson().toJson(result);
    }

}
