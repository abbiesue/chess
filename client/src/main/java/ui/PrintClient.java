package ui;

import chess.ChessGame;
import model.GameData;
import records.JoinRequest;
import records.ListRequest;
import records.ListResult;
import server.ResponseException;
import server.ServerFacade;
import java.util.Arrays;

public class PrintClient {
    static final String WHITE = "WHITE";

    private final ServerFacade server;
    private final BoardPrinter printer = new BoardPrinter();
    String authToken;
    String playerColor;

    public PrintClient(ServerFacade server, String authToken) {
        this.server = server;
        this.authToken = authToken;
    }

    public String eval(String input) {
        try {
            var tokens = input.toLowerCase().split(" ");
            var cmd = (tokens.length > 0) ? tokens[0] : "help";
            var params = Arrays.copyOfRange(tokens, 1, tokens.length);
            return switch (cmd) {
                case "join" -> join(params);
                case "observe" -> observe(params);
                case "quit" -> "quit";
                default -> help();
            };
        } catch (ResponseException ex) {
            return ex.getMessage();
        }
    }

    public String join(String... params) throws ResponseException {
        if (params.length >= 2) {
            if (!isInteger(params[0])) {
                throw new ResponseException(400, "ID must be an integer");
            }
            int listID = Integer.parseInt(params[0]);
            int gameID = getIDfromList(listID);
            playerColor = params[1].toUpperCase();
            server.join(new JoinRequest(authToken, playerColor, gameID));
            printer.printFromGame(getGame(gameID), playerColor);
            return " ";

        }
        throw new ResponseException(400, "Expected: <ID> [WHITE|BLACK]");
    }

    public String observe(String...params) throws ResponseException {
        if (params.length >= 1) {
            playerColor = WHITE;
            if (!isInteger(params[0])) {
                throw new ResponseException(400, "ID must be an integer");
            }
            int listID = Integer.parseInt(params[0]);
            int gameID = getIDfromList(listID);
            printer.printFromGame(getGame(gameID), playerColor);
            return "\n observing...";
        }
        throw new ResponseException(400, "Expected: <ID>");
    }

    public String help() {
        return """
                create <GAME_NAME> - to create a game
                list - to list games
                join <ID> [WHITE|BLACK] - to join a game
                observe<ID> - to observe a game
                logout - to logout
                quit - to exit program
                help - to list options
                """;
    }

    private ChessGame getGame(int gameID) throws ResponseException {
        var games = server.list(new ListRequest(authToken)).games();
        for (int i = 0; i < games.size(); i++) {
            if (games.get(i).gameID() == gameID) {
                return games.get(i).game();
            }
        }
        throw new ResponseException(400, "Game not Found");
    }

    public int getIDfromList(int listID) throws ResponseException {

        ListResult listResult = server.list(new ListRequest(authToken));
        if (listID > listResult.games().size() || listID < 0) {
            return -1;
        }
        GameData game = listResult.games().get(listID-1);
        return game.gameID();
    }

    public static boolean isInteger(String s) {
        try {
            Integer.parseInt(s);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

}
