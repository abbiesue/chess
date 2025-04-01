package ui;

import chess.ChessGame;
import model.GameData;
import records.*;
import server.ResponseException;
import server.ServerFacade;

import java.util.Arrays;
import java.util.List;

public class PostloginClient {
    static final String WHITE = "WHITE";

    private final ServerFacade server;
    private final BoardPrinter printer = new BoardPrinter();
    String authToken;
    String playerColor;

    public PostloginClient(ServerFacade server, String authToken) {
        this.server = server;
        this.authToken = authToken;
    }

    public String eval(String input) {
        try {
            var tokens = input.toLowerCase().split(" ");
            var cmd = (tokens.length > 0) ? tokens[0] : "help";
            var params = Arrays.copyOfRange(tokens, 1, tokens.length);
            return switch (cmd) {
                case "create" -> create(params);
                case "list" -> list();
                case "join" -> join(params);
                case "observe" -> observe(params);
                case "logout" -> logout();
                case "quit" -> "quit";
                default -> help();
            };
        } catch (ResponseException ex) {
            return ex.getMessage();
        }
    }

    public String create(String... params) throws ResponseException {
        if (params.length >= 1) {
            var gameName = params[0];
            server.create(new CreateRequest(authToken, gameName));
            return "Successfully created " + gameName;
        }
        throw new ResponseException(400, "Expected: <GAME_NAME>");
    }

    public String list() throws ResponseException {
        ListResult result = server.list(new ListRequest(authToken));
        List<GameData> games = result.games();
        String listString = "";
        GameData game;
        int index;
        for (int i = 0; i < games.size(); i++) {
            game = games.get(i);
            index = i+1;
            listString = listString.concat("\n" + index +
                    ": NAME - " + game.gameName() +
                    " WHITE - " + game.whiteUsername() + " BLACK - " + game.blackUsername());
        }
        return listString;
    }

    public String join(String... params) throws ResponseException {
        if (params.length >= 2) {
            if (!isInteger(params[0])) {
                throw new ResponseException(400, "ID must be an integer");
            }
            int listID = Integer.parseInt(params[0]);
            int gameID = getIDFromList(listID);
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
            int gameID = getIDFromList(listID);
            printer.printFromGame(getGame(gameID), playerColor);
            return "\n observing...";
        }
        throw new ResponseException(400, "Expected: <ID>");
    }


    public String logout() throws ResponseException {
        server.logout(new LogoutRequest(authToken));
        return "logged out";
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

    public int getIDFromList(int listID) throws ResponseException {

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
