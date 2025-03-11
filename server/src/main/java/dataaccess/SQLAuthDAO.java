package dataaccess;

import model.AuthData;
import server.ResponseException;

public class SQLAuthDAO extends SQLDAO implements AuthDAO{
    private String[] createStatements = {
            """
            CREATE TABLE IF NOT EXISTS userData (
            `authToken` varchar(256) NOT NULL,
            `username` varchar(256) NOT NULL,
            PRIMARY KEY(`username`),
            INDEX indx_auth(authToken)
            ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci
            """
    };

    public SQLAuthDAO() throws ResponseException, DataAccessException {configureDatabase(createStatements);}

    @Override
    public void createAuth(AuthData authdata) {

    }

    @Override
    public AuthData getAuth(String authtoken) {
        return null;
    }

    @Override
    public void deleteAuth(String authToken) {

    }

    @Override
    public void clear() {

    }
}
