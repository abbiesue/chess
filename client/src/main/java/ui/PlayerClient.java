package ui;

import chess.ChessGame;
import model.GameData;
import records.JoinRequest;
import records.ListRequest;
import records.ListResult;
import server.ResponseException;
import server.ServerFacade;
import websocket.ServerMessageObserver;
import websocket.WebSocketFacade;

import java.util.Arrays;

public class PlayerClient extends GameClient{
    private final ServerFacade server;
    private String serverURL;
    private WebSocketFacade ws;
    ServerMessageObserver observer;

    private String username = null;
    private String playerColor;
    private int gameID;
    private String authToken;



    public PlayerClient(ServerFacade server, String serverURL, ServerMessageObserver observer) {
        this.server = server;
        this.serverURL = serverURL;
        this.observer = observer;
    }

    public String eval(String input) {
        try {
            var tokens = input.toLowerCase().split(" ");
            var cmd = (tokens.length > 0) ? tokens[0] : "help";
            var params = Arrays.copyOfRange(tokens, 1, tokens.length);
            return switch (cmd) {
                case "join" -> join(params);
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

    private String join(String... params) throws ResponseException {
        int listID = Integer.parseInt(params[0]);
        gameID = getIDFromList(listID);
        playerColor = params[1].toUpperCase();
        server.join(new JoinRequest(authToken, playerColor, gameID));
        return "\n joining game...";

        //ws = new WebSocketFacade(serverURL, observer);
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

    public int getIDFromList(int listID) throws ResponseException {
        ListResult listResult = server.list(new ListRequest(authToken));
        if (listID > listResult.games().size() || listID < 0) {
            return -1;
        }
        GameData game = listResult.games().get(listID-1);
        return game.gameID();
    }
}
