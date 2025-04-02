package ui;

import server.ResponseException;
import server.ServerFacade;

import java.util.Arrays;

public class PlayerClient extends GameClient{
    private final ServerFacade server;

    public PlayerClient(ServerFacade server) {
        this.server = server;
    }

    public String eval(String input) {
        try {
            var tokens = input.toLowerCase().split(" ");
            var cmd = (tokens.length > 0) ? tokens[0] : "help";
            var params = Arrays.copyOfRange(tokens, 1, tokens.length);
            return switch (cmd) {
                case "make" -> makeMove(params);
                case "highlight" -> highlightLegalMoves(params);
                case "redraw" -> redrawChessBoard(params);
                case "resign" -> resign();
                case "leave" -> "You've left gameplay.";
                default -> help();
            };
        } catch (ResponseException ex) {
            return ex.getMessage();
        }
    }

    public String makeMove(String... params) throws ResponseException{
        // add code to make sure that even if there is the right number of parameters they are the right parameters
        if (params.length < 1) {
            return "Did you mean \"make move <START_POSITION> <END_POSITION>\"? Please try again.";
        } else {
            return "make move is under construction...";
        }
    }

    public String resign() {
        return "resign is under construction...";
    }

    public String help() {
        return """
                make move <START_POSITION> <END_POSITION> - to take your turn
                highlight legal moves <START_POSITION> - to highlight legal moves on the board for a specific piece
                redraw chess board - to redraw the chessboard
                resign - to forfeit and end the game
                leave - to exit the game window
                help - to list options
                """;
    }

}
