package dataaccess;

import com.google.gson.Gson;
import model.UserData;
import org.mindrot.jbcrypt.BCrypt;
import server.ResponseException;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SQLUserDAO extends SQLDAO implements UserDAO{
    private String[] createStatements = {
            """
            CREATE TABLE IF NOT EXISTS userData (
            `username` varchar(256) NOT NULL,
            `passwordHash` varchar(256) NOT NULL,
            `email` varchar(256) NOT NULL,
            PRIMARY KEY(`username`),
            INDEX indx_passwordHash (passwordHash),
            INDEX indx_email (email)
            ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci
            """
    };

    public SQLUserDAO() throws ResponseException, DataAccessException {configureDatabase(createStatements);}

    @Override
    public void createUser(UserData userData) throws ResponseException {
        var statement = "INSERT INTO userData (username, passwordHash, email) VALUES (?,?,?)";
        String username = userData.username();
        String passwordHash = hashPassword(userData.password());
        String email = userData.email();
        executeUpdate(statement, username, passwordHash, email);
    }

    @Override
    public UserData getUser(String username) throws ResponseException {
        try (var conn = DatabaseManager.getConnection()) {
            var statement = "SELECT username, passwordHash, email FROM userData WHERE username=?";
            try (var ps = conn.prepareStatement(statement)) {
                ps.setString(1, username);
                try (var rs = ps.executeQuery()) {
                    if (rs.next()) {
                        return readUser(rs);
                    }
                }
            }
        } catch (Exception e) {
            throw new ResponseException(500, String.format("Unable to read data: %s", e.getMessage()));
        }
        return null;
    }

    @Override
    public void clear() throws ResponseException {
        var statement = "TRUNCATE userData";
        executeUpdate(statement);
    }

    private String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    private UserData readUser(ResultSet rs) throws SQLException {
        var username = rs.getString("username");
        var passwordHash = rs.getString("passwordHash");
        var email = rs.getString("email");
        return new UserData(username, passwordHash, email);
    }
}
