package service;

import dataaccess.MemoryAuthDAO;
import dataaccess.MemoryGameDAO;
import dataaccess.MemoryUserDAO;
import model.GameData;
import records.*;
import server.ResponseException;

import java.util.List;

public class GameService {
    MemoryGameDAO gameDAO;
    MemoryAuthDAO authDAO;

    public GameService(MemoryGameDAO gameDAO, MemoryAuthDAO authDAO) {
        this.gameDAO = gameDAO;
        this.authDAO = authDAO;
    }

    public ListResult list(ListRequest listRequest) throws ResponseException {
        //check auth
        if (authDAO.getAuth(listRequest.authToken()) == null) {
            throw new ResponseException(401, "Error: unauthorized");
        }
        //implement
        return new ListResult((List<GameData>) gameDAO.listGames());
    }

    public CreateResult create(CreateRequest createRequest) throws ResponseException {
        //check auth
        if (authDAO.getAuth(createRequest.authToken()) == null) {
            throw new ResponseException(401, "Error: unauthorized");
        }
        if (createRequest.gameName() == null || createRequest.gameName().equals("")) {
            throw new ResponseException(400, "Error: bad request");
        }
        //implement
        GameData newGame = gameDAO.createGame(createRequest.gameName());
        return new CreateResult(newGame.gameID());
    }

    public void join(JoinRequest joinRequest) throws ResponseException {
        //check auth
        if (authDAO.getAuth(joinRequest.authToken()) == null) {
            throw new ResponseException(401, "Error: unauthorized");
        }
        //bad request 401 (gameID doesn't exist or if any fields are empty or null or if player color does not equal BlLACK or WHITE)
        //already taken 403 (that color on that game is taken)
        //otherwise,
        // get username by auth
        // update by gameID the correct color with the username
        // return JoinResult
    }

}

