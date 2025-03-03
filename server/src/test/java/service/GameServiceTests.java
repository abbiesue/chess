package service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class GameServiceTests {
    public GameService gameService;

    @BeforeEach
    public void init() {
        gameService = new GameService();
    }

    @Test
    @DisplayName("createGame Success")
    public void createGameSuccess() {
        //
    }
}
