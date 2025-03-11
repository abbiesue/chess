package service;

import dataaccess.*;
import records.ClearResult;

public class ClearService {
    UserDAO userDAO;
    GameDAO gameDAO;
    AuthDAO authDAO;

    public ClearService(UserDAO userDAO, GameDAO gameDAO, AuthDAO authDAO) {
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
