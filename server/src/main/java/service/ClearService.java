package service;

import dataaccess.MemoryAuthDAO;
import dataaccess.MemoryGameDAO;
import dataaccess.MemoryUserDAO;
import records.ClearResult;
import server.ResponseException;

public class ClearService {
    MemoryUserDAO userDAO;
    MemoryGameDAO gameDAO;
    MemoryAuthDAO authDAO;

    public ClearService(MemoryUserDAO userDAO, MemoryGameDAO gameDAO, MemoryAuthDAO authDAO) {
        this.userDAO = userDAO;
        this.gameDAO = gameDAO;
        this.authDAO = authDAO;
    }

    public ClearResult clear() {
        userDAO.clear();
        gameDAO.clear();
        authDAO.clear();
        return new ClearResult();
    }
}
