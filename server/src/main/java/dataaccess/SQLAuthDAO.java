package dataaccess;

import model.AuthData;
import server.ResponseException;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SQLAuthDAO extends SQLDAO implements AuthDAO{
    private String[] createStatements = {
            """
            CREATE TABLE IF NOT EXISTS authData (
            `id` int NOT NULL AUTO_INCREMENT,
            `authToken` varchar(256) NOT NULL,
            `username` varchar(256) NOT NULL,
            PRIMARY KEY (`id`), 
            INDEX indx_auth(authToken),  -- Regular index (NOT UNIQUE)
            INDEX indx_user(username)    -- Regular index (NOT UNIQUE)
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
            var statement = "SELECT username, authToken FROM authData WHERE authToken=?";
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
    public void clear() throws ResponseException, DataAccessException {
        var statement = "DROP TABLE IF EXISTS authData";
        executeUpdate(statement);
        configureDatabase(createStatements);
    }

    private AuthData readAuth(ResultSet rs) throws SQLException {
        var username = rs.getString("username");
        var authToken = rs.getString("authToken");
        return new AuthData(authToken, username);
    }
}
