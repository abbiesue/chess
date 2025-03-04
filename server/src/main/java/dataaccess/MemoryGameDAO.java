package dataaccess;

import chess.ChessGame;
import model.GameData;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class MemoryGameDAO implements GameDAO {
    public List<GameData> Games;
    private int nextID = 1;

    public MemoryGameDAO() {
        Games = new ArrayList<>();
    }

    @Override
    public GameData createGame(String gameName) {
        GameData newGame = new GameData(nextID++, null, null, gameName, new ChessGame());
        Games.add(newGame);
        return newGame;
    }

    @Override
    public GameData getGame(int gameID) {
        if (Games.isEmpty()) {
            return null;
        } else {
            for (int i = 0; i < Games.size(); i++) {
                if (Games.get(i).gameID() == gameID) {
                    return Games.get(i);
                }
            }
        }
        return null;
    }

    @Override
    public Collection<GameData> listGames() {
        return Games;
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
        Games.add(newGame);
    }

    @Override
    public void deleteGame(int gameID) {
        if (Games.isEmpty()) {
            return;
        } else {
            for (int i = 0; i < Games.size(); i++) {
                if (Games.get(i).gameID() == gameID) {
                    Games.remove(i);
                }
            }
        }
    }

    @Override
    public void clear() {
        for (int i = Games.size()-1; i >= 0; i--){
            Games.remove(i);
        }
        Games = new ArrayList<>();
    }
}
