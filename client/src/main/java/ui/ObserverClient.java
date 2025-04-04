package ui;

import server.ResponseException;
import server.ServerFacade;

import java.util.Arrays;

public class ObserverClient extends GameClient{
    private final ServerFacade server;

    public ObserverClient(ServerFacade server) {
        this.server = server;
    }

    public String eval(String input) {
        try {
            var tokens = input.toLowerCase().split(" ");
            var cmd = (tokens.length > 0) ? tokens[0] : "help";
            var params = Arrays.copyOfRange(tokens, 1, tokens.length);
            return switch (cmd) {
                case "highlight" -> highlightLegalMoves(params);
                case "redraw" -> redrawChessBoard(params);
                case "leave" -> "You've left gameplay.";
                default -> help();
            };
        } catch (ResponseException ex) {
            return ex.getMessage();
        }
    }

    public String help() {
        return """
                highlight legal moves <START_POSITION> - to highlight legal moves on the board for a specific piece
                redraw chess board - to redraw the chessboard
                leave - to exit the game window
                help - to list options
                """;
    }
}
