package ui;

import server.ResponseException;
import server.ServerFacade;
import websocket.ServerMessageObserver;
import websocket.WebSocketFacade;

import java.util.Arrays;

public class ObserverClient extends GameClient implements ServerMessageObserver{
    static final String WHITE = "WHITE";
    private WebSocketFacade ws;

    public ObserverClient(String authToken, ServerFacade server, String serverURL) throws ResponseException {
        this.authToken = authToken;
        this.server = server;
        ws = new WebSocketFacade(serverURL, this);
    }

    public String eval(String input) {
        try {
            var tokens = input.toLowerCase().split(" ");
            var cmd = (tokens.length > 0) ? tokens[0] : "help";
            var params = Arrays.copyOfRange(tokens, 1, tokens.length);
            return switch (cmd) {
                case "observe" -> observe(params);
                case "highlight" -> highlightLegalMoves(params);
                case "redraw" -> redrawChessBoard(params);
                case "leave" -> leave();
                default -> help();
            };
        } catch (ResponseException ex) {
            return ex.getMessage();
        }
    }

    private String observe(String... params) throws ResponseException {
        playerColor = WHITE;
        int listID = Integer.parseInt(params[0]);
        gameID = getIDFromList(listID);
        ws.connect(authToken, gameID, null);
        return "observing game...";
    }

    public String leave() throws ResponseException {
        ws.leave(authToken, gameID);
        return "You've left gameplay.";
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
