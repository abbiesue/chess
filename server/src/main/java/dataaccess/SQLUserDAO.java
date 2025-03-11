package dataaccess;

import model.UserData;
import server.ResponseException;

public class SQLUserDAO extends SQLDAO implements UserDAO{
    private String[] createStatements = {
            """
            CREATE TABLE IF NOT EXISTS userData (
            `username` varchar(256) NOT NULL,
            `passwordHash` varchar(256) NOT NULL,
            `email` varchar(256) NOT NULL,
            PRIMARY KEY(`username`),
            INDEX indx_passwordHash (password_hash),
            INDEX indx_email (email)
            ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci
            """
    };

    public SQLUserDAO() throws ResponseException, DataAccessException {configureDatabase(createStatements);}

    @Override
    public void createUser(UserData userData) {

    }

    @Override
    public UserData getUser(String username) {
        return null;
    }

    @Override
    public void clear() {

    }
}
