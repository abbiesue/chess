package dataaccess;

import model.UserData;
import server.ResponseException;

public class SQLUserDAO extends SQLDAO implements UserDAO{
    private String[] createStatements = {

    };

    public SQLUserDAO() throws ResponseException {configureDatabase(createStatements);}

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
