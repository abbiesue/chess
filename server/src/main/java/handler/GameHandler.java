package handler;

import dataaccess.MemoryAuthDAO;
import dataaccess.MemoryGameDAO;
import model.GameData;
import service.GameService;

public class GameHandler {
    public GameService gameService;
    MemoryGameDAO gameDAO;
    MemoryAuthDAO authDAO;

    public GameHandler(MemoryGameDAO gameDAO, MemoryAuthDAO authDAO) {
        this.gameDAO = gameDAO;
        this.authDAO = authDAO;
        gameService = new GameService(gameDAO, authDAO);;
    }

    public Object createGame(GameData gameData) {
        return null;
    }
}
