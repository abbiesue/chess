package service;

import dataaccess.MemoryAuthDAO;
import dataaccess.MemoryGameDAO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class GameServiceTests {
    MemoryGameDAO gameDAO = new MemoryGameDAO();
    MemoryAuthDAO authDAO = new MemoryAuthDAO();
    public GameService gameService;

    @BeforeEach
    public void init() {
        gameService = new GameService(gameDAO, authDAO);
    }

    @Test
    @DisplayName("createGame Success")
    public void createGameSuccess() {
        //
    }
}
