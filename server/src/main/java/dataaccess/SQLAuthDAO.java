package dataaccess;

import model.AuthData;
import model.UserData;
import server.ResponseException;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SQLAuthDAO extends SQLDAO implements AuthDAO{
    private String[] createStatements = {
            """
            CREATE TABLE IF NOT EXISTS authData (
            `authToken` varchar(256) NOT NULL,
            `username` varchar(256) NOT NULL,
            PRIMARY KEY(`username`),
            INDEX indx_auth(authToken)
            ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci
            """
    };

    public SQLAuthDAO() throws ResponseException, DataAccessException {configureDatabase(createStatements);}

    @Override
    public void createAuth(AuthData authdata) throws ResponseException {
        var statement = "INSERT INTO authData (authToken, username) VALUES (?,?)";
        String authToken = authdata.authToken();
        String username = authdata.username();
        executeUpdate(statement, authToken, username);
    }

    @Override
    public AuthData getAuth(String authToken) throws ResponseException {
        try (var conn = DatabaseManager.getConnection()) {
            var statement = "SELECT username, authToken FROM authData WHERE username=?";
            try (var ps = conn.prepareStatement(statement)) {
                ps.setString(1, authToken);
                try (var rs = ps.executeQuery()) {
                    if (rs.next()) {
                        return readAuth(rs);
                    }
                }
            }
        } catch (Exception e) {
            throw new ResponseException(500, String.format("Unable to read data: %s", e.getMessage()));
        }
        return null;
    }

    @Override
    public void deleteAuth(String authToken) throws ResponseException {
        var statement = "DELETE FROM authData WHERE authToken=?";
        executeUpdate(statement, authToken);
    }

    @Override
    public void clear() throws ResponseException {
        var statement = "TRUNCATE authData";
        executeUpdate(statement);
    }

    private AuthData readAuth(ResultSet rs) throws SQLException {
        var username = rs.getString("username");
        var authToken = rs.getString("authToken");
        return new AuthData(authToken, username);
    }
}
