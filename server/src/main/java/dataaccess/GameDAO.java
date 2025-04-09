package dataaccess;

import chess.ChessGame;
import model.GameData;
import Exceptions.ResponseException;

import java.util.Collection;

public interface GameDAO {
    GameData createGame(String gameName) throws ResponseException;
    GameData getGame(int gameID) throws ResponseException;
    Collection<GameData> listGames() throws ResponseException;
    void updateGame(int gameID, ChessGame.TeamColor playerColor, String playerUsername) throws ResponseException;
    void deleteGame(int gameID) throws ResponseException;
    void clear() throws ResponseException, DataAccessException;
    boolean isGameOver(int gameID) throws ResponseException;
}
