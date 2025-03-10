package dataaccess;

import model.AuthData;
import server.ResponseException;

public class SQLAuthDAO extends SQLDAO implements AuthDAO{
    private String[] createStatements = {

    };

    public SQLAuthDAO() throws ResponseException {configureDatabase(createStatements);}

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
