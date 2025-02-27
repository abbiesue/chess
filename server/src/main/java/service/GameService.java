package service;

import chess.ChessGame;
import model.GameData;

import java.util.List;

public class GameService {
    public ListResult list(ListRequest listRequest) {
        //implement
        return null;
    }
    public CreateResult create(CreateRequest createRequest) {
        //implement
        return null;
    }
    public void join(JoinRequest joinRequest) {

    }

    //records:
    public record ListRequest(String authToken){}
    public record ListResult(List<GameData> games){}
    public record CreateRequest(String authToken, String gameName){}
    public record CreateResult(String gameID) {}
    public record JoinRequest(String authtoken, ChessGame.TeamColor playerColor, String gameID) {}
}
