package dataaccess;

import chess.ChessGame;
import model.GameData;
import server.ResponseException;

import java.util.Collection;

public class SQLGameDAO extends SQLDAO implements GameDAO {
    private String[] createStatements = {
            """
            CREATE TABLE IF NOT EXISTS userData (
            `gameID` int NOT NULL AUTO_INCREMENT,
            `whiteUsername` varchar(256) NOT NULL,
            `blackUsername` varchar(256) NOT NULL,
            'gameName` varchar(256) NOT NULL,
            `game_json` TEXT DEFAULT NULL,
            PRIMARY KEY(`gameID`),
            INDEX indx_whiteUsername (whiteUsername),
            INDEX indx_blackUsername (blackUsername),
            INDEX indx_gameName (gameName)
            ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci
            """
    };

    public SQLGameDAO() throws ResponseException, DataAccessException {configureDatabase(createStatements);}

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
