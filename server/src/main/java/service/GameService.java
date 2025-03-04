package service;

import chess.ChessGame;
import dataaccess.MemoryAuthDAO;
import dataaccess.MemoryGameDAO;
import model.GameData;
import records.*;
import server.ResponseException;
import java.util.List;
import java.util.Objects;

public class GameService {
    final ResponseException BAD_REQUEST = new ResponseException(400, "Error: bad request");
    final ResponseException UNAUTHORIZED = new ResponseException(401, "Error: unauthorized");
    final ResponseException TAKEN = new ResponseException(403, "Error: already taken");


    MemoryGameDAO gameDAO;
    MemoryAuthDAO authDAO;

    public GameService(MemoryGameDAO gameDAO, MemoryAuthDAO authDAO) {
        this.gameDAO = gameDAO;
        this.authDAO = authDAO;
    }

    public ListResult list(ListRequest listRequest) throws ResponseException {
        //check auth
        if (authDAO.getAuth(listRequest.authToken()) == null) {
            throw UNAUTHORIZED;
        }
        //implement
        return new ListResult((List<GameData>) gameDAO.listGames());
    }

    public CreateResult create(CreateRequest createRequest) throws ResponseException {
        //check auth
        if (authDAO.getAuth(createRequest.authToken()) == null) {
            throw UNAUTHORIZED;
        }
        if (createRequest.gameName() == null || createRequest.gameName().equals("")) {
            throw BAD_REQUEST;
        }
        //implement
        GameData newGame = gameDAO.createGame(createRequest.gameName());
        return new CreateResult(newGame.gameID());
    }

    public JoinResult join(JoinRequest joinRequest) throws ResponseException {
        //throw exceptions
        if (joinRequest.gameID() == null || joinRequest.playerColor() == null) {
            throw BAD_REQUEST;
        }
        if (gameDAO.getGame(joinRequest.gameID()) == null) {
            throw BAD_REQUEST;
        }
        if (!Objects.equals(joinRequest.playerColor(), "WHITE") && !Objects.equals(joinRequest.playerColor(), "BLACK")) {
            throw BAD_REQUEST;
        }
        if (authDAO.getAuth(joinRequest.authToken()) == null) {
            throw UNAUTHORIZED;
        }
        GameData gameWanted = gameDAO.getGame(joinRequest.gameID());
        if (joinRequest.playerColor().equals("WHITE")) {
            if (gameWanted.whiteUsername() != null) {
                throw TAKEN;
            } else {
                gameDAO.updateGame(joinRequest.gameID(), ChessGame.TeamColor.WHITE, authDAO.getAuth(joinRequest.authToken()).username());
            }
        } else {
            if (gameWanted.blackUsername() != null) {
                throw TAKEN;
            } else {
                gameDAO.updateGame(joinRequest.gameID(), ChessGame.TeamColor.BLACK, authDAO.getAuth(joinRequest.authToken()).username());
            }
        }
        return new JoinResult();
    }

}

