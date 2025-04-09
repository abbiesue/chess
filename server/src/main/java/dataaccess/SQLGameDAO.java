package dataaccess;

import chess.ChessGame;
import com.google.gson.Gson;
import model.GameData;
import Exceptions.ResponseException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class SQLGameDAO extends SQLDAO implements GameDAO {
    private String[] createStatements = {
            """
            CREATE TABLE IF NOT EXISTS gameData (
            `gameID` int NOT NULL AUTO_INCREMENT,
            `whiteUsername` varchar(256) DEFAULT NULL,
            `blackUsername` varchar(256) DEFAULT NULL,
            `gameName` varchar(256) NOT NULL,
            `gameJson` TEXT DEFAULT NULL,
            `finished` BOOLEAN DEFAULT FALSE,
            PRIMARY KEY(`gameID`),
            INDEX indx_whiteUsername (whiteUsername),
            INDEX indx_blackUsername (blackUsername),
            INDEX indx_gameName (gameName)
            ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci
            """
    };

    public SQLGameDAO() throws ResponseException, DataAccessException {configureDatabase(createStatements);}

    @Override
    public GameData createGame(String gameName) throws ResponseException {
        var statement = "INSERT INTO gameData (whiteUsername, blackUsername, gameName, gameJson) VALUES (?,?,?,?)";
        ChessGame game = new ChessGame();
        var gameJson = new Gson().toJson(game);
        var id = executeUpdate(statement, null, null, gameName, gameJson);
        return new GameData(id, null, null, gameName, game);
    }

    @Override
    public GameData getGame(int gameID) throws ResponseException {
        try (var conn = DatabaseManager.getConnection()) {
            var statement = "SELECT gameID, whiteUsername, blackUsername, gameName, gameJson FROM gameData WHERE gameID=?";
            try (var ps = conn.prepareStatement(statement)) {
                ps.setInt(1, gameID);
                try (var rs = ps.executeQuery()) {
                    if (rs.next()) {
                        return readGame(rs);
                    }
                }
            }
        } catch (Exception e) {
            throw new ResponseException(500, String.format("Unable to read data: %s", e.getMessage()));
        }
        return null;
    }

    @Override
    public Collection<GameData> listGames() throws ResponseException {
        List<GameData> games = new ArrayList<>();
        try (var conn = DatabaseManager.getConnection()) {
            var statement = "SELECT gameID, whiteUsername, blackUsername, gameName, gameJson FROM gameData";
            var ps = conn.prepareStatement(statement);
            var rs = ps.executeQuery();
            while (rs.next()) {
                if (!isGameOver(Integer.parseInt(rs.getString("gameID")))) {
                    games.add(readGame(rs));
                }
            }
        } catch (Exception e) {
            throw new ResponseException(500, String.format("Unable to read data: %s", e.getMessage()));
        }
        return games;
    }

    @Override
    public void updateGame(int gameID, ChessGame.TeamColor playerColor, String playerUsername) throws ResponseException {
        GameData oldGame = getGame(gameID);

        var whiteUsername = oldGame.whiteUsername();
        var blackUsername = oldGame.blackUsername();
        var gameName = oldGame.gameName();
        ChessGame game = oldGame.game();

        if (playerColor == ChessGame.TeamColor.WHITE) {
            whiteUsername = playerUsername;
        } else {
            blackUsername = playerUsername;
        }

        deleteGame(gameID);
        var statement = "INSERT INTO gameData (gameID, whiteUsername, blackUsername, gameName, gameJson) VALUES (?,?,?,?,?)";
        var gameJson = new Gson().toJson(game);
        executeUpdate(statement, gameID, whiteUsername, blackUsername, gameName, gameJson);
    }

    public void updateAfterMove(int gameID, ChessGame newGame) throws ResponseException {
        GameData oldGame = getGame(gameID);
        var whiteUsername = oldGame.whiteUsername();
        var blackUsername = oldGame.blackUsername();
        var gameName = oldGame.gameName();
        ChessGame game = newGame;

        deleteGame(gameID);
        var statement = "INSERT INTO gameData (gameID, whiteUsername, blackUsername, gameName, gameJson) VALUES (?,?,?,?,?)";
        var gameJson = new Gson().toJson(game);
        executeUpdate(statement, gameID, whiteUsername, blackUsername, gameName, gameJson);
    }

    @Override
    public void deleteGame(int gameID) throws ResponseException {
        var statement = "DELETE FROM gameData WHERE gameID=?";
        executeUpdate(statement, gameID);
    }

    @Override
    public void clear() throws ResponseException, DataAccessException {
        var statement = "DROP TABLE IF EXISTS gameData";
        executeUpdate(statement);
        configureDatabase(createStatements);
    }

    private GameData readGame(ResultSet rs) throws SQLException {
        var gameID = rs.getInt("gameID");
        var whiteUsername = rs.getString("whiteUsername");
        var blackUsername = rs.getString("blackUsername");
        var gameName = rs.getString("gameName");
        var gameJson = rs.getString("gameJson");
        ChessGame game = new Gson().fromJson(gameJson,ChessGame.class);
        GameData gameData = new GameData(gameID, whiteUsername, blackUsername, gameName, game);
        return gameData;
    }

    public void setGameOver(int gameID) throws ResponseException {
        String statement = "UPDATE gameData SET finished = TRUE WHERE gameID = ?";
        executeUpdate(statement, gameID);
    }

    public boolean isGameOver(int gameID) throws ResponseException {
        try (var conn = DatabaseManager.getConnection()) {
            var statement = "SELECT finished FROM gameData WHERE gameID=?";
            try (var ps = conn.prepareStatement(statement)) {
                ps.setInt(1, gameID);
                try (var rs = ps.executeQuery()) {
                    if (rs.next()) {
                        return rs.getBoolean("finished");
                    }
                }
            }
        } catch (Exception e) {
            throw new ResponseException(500, String.format("Unable to read data: %s", e.getMessage()));
        }
        return true;
    }
}
