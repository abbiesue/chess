package handler;

import com.google.gson.Gson;
import records.ClearResult;
import server.ResponseException;
import service.ClearService;
import spark.Request;
import spark.Response;

public class ClearHandler {
    public Object clear(Request request, Response response){
        ClearResult result;
        try {
            result = ClearService.clear();
        } catch (ResponseException e){
            return e.toJson();
        }
        return new Gson().toJson(result);
    }

}
