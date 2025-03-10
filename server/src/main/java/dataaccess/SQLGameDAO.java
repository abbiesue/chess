package dataaccess;

import chess.ChessGame;
import model.GameData;
import server.ResponseException;

import java.util.Collection;

public class SQLGameDAO extends SQLDAO implements GameDAO {
    private String[] createStatements = {

    };

    public SQLGameDAO() throws ResponseException {configureDatabase(createStatements);}

    @Override
    public GameData createGame(String gameName) {
        return null;
    }

    @Override
    public GameData getGame(int gameID) {
        return null;
    }

    @Override
    public Collection<GameData> listGames() {
        return null;
    }

    @Override
    public void updateGame(int gameID, ChessGame.TeamColor playerColor, String playerUsername) {

    }

    @Override
    public void deleteGame(int gameID) {

    }

    @Override
    public void clear() {

    }
}
