package handler;

import model.GameData;
import service.GameService;

public class GameHandler {
    public GameService gameService;

    public GameHandler() {
        gameService = new GameService();;
    }

    public Object createGame(GameData gameData) {

    }
}
