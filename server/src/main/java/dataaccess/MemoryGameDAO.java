package dataaccess;

import chess.ChessGame;
import model.GameData;
import server.ResponseException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class MemoryGameDAO implements GameDAO {
    public List<GameData> games;
    private int nextID = 1;

    public MemoryGameDAO() {
        games = new ArrayList<>();
    }

    @Override
    public GameData createGame(String gameName) {
        GameData newGame = new GameData(nextID++, null, null, gameName, new ChessGame());
        games.add(newGame);
        return newGame;
    }

    @Override
    public GameData getGame(int gameID) {
        if (games.isEmpty()) {
            return null;
        } else {
            for (int i = 0; i < games.size(); i++) {
                if (games.get(i).gameID() == gameID) {
                    return games.get(i);
                }
            }
        }
        return null;
    }

    @Override
    public Collection<GameData> listGames() {
        return games;
    }

    @Override
    public void updateGame(int gameID, ChessGame.TeamColor playerColor, String playerUsername) {
        GameData oldGame = getGame(gameID);
        GameData newGame;
        if (playerColor == ChessGame.TeamColor.WHITE) {
            newGame = new GameData(oldGame.gameID(), playerUsername, oldGame.blackUsername(), oldGame.gameName(), oldGame.game());
        } else {
            newGame = new GameData(oldGame.gameID(), oldGame.whiteUsername(), playerUsername, oldGame.gameName(), oldGame.game());
        }
        deleteGame(oldGame.gameID());
        games.add(newGame);
    }

    @Override
    public void deleteGame(int gameID) {
        if (games.isEmpty()) {
            return;
        } else {
            for (int i = 0; i < games.size(); i++) {
                if (games.get(i).gameID() == gameID) {
                    games.remove(i);
                }
            }
        }
    }

    @Override
    public void clear() {
        for (int i = games.size()-1; i >= 0; i--){
            games.remove(i);
        }
        games = new ArrayList<>();
    }

    @Override
    public boolean isGameOver(int gameID) throws ResponseException {
        return false;
    }
}
