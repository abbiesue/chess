package dataaccess;

import chess.ChessGame;
import model.GameData;

import java.util.Collection;

public interface GameDAO {
    GameData createGame(String gameName);
    GameData getGame(int gameID);
    Collection<GameData> listGames();
    void updateGame(int gameID, ChessGame.TeamColor playerColor, String playerUsername);
    void deleteGame(int gameID);
    void clear();
}
