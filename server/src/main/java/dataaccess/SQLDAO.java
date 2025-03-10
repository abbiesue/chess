package dataaccess;

import server.ResponseException;

public abstract class SQLDAO {
    private String[] createStatements;

    void configureDatabase(String[] createStatements) throws ResponseException {
        // basically only used in constructor and clear. configures tha database from scratch.
    }

    private int executeUpdate(String statement, Object...params) throws ResponseException {
        // to be used by member functions to update the Game SQL table.
        return 1;
    }

}
