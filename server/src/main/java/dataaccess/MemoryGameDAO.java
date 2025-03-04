package dataaccess;

import chess.ChessGame;
import model.GameData;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class MemoryGameDAO implements GameDAO {
    private List<GameData> Games;
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
    public void updateGame() {

    }

    @Override
    public void deleteGame() {

    }
}
