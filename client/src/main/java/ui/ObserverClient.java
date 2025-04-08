package ui;

import chess.ChessGame;
import model.GameData;
import records.ListRequest;
import records.ListResult;
import server.ResponseException;
import server.ServerFacade;
import websocket.ServerMessageObserver;
import websocket.WebSocketFacade;

import java.util.Arrays;

public class ObserverClient extends GameClient{
    static final String WHITE = "WHITE";
    private final ServerFacade server;
    private String serverURL;
    private WebSocketFacade ws;
    ServerMessageObserver observer;

    private String username = null;
    private String playerColor;
    private int gameID;
    private String authToken;

    public ObserverClient(String authToken, ServerFacade server, String serverURL, ServerMessageObserver observer) throws ResponseException {
        this.authToken = authToken;
        this.server = server;
        this.serverURL = serverURL;
        ws = new WebSocketFacade(serverURL, observer);
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
        ws.connect(authToken, gameID);
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

    public int getIDFromList(int listID) throws ResponseException {
        ListResult listResult = server.list(new ListRequest(authToken));
        if (listID > listResult.games().size() || listID < 0) {
            return -1;
        }
        GameData game = listResult.games().get(listID-1);
        return game.gameID();
    }

}
