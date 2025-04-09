package ui;

import model.GameData;
import records.*;
import Exceptions.ResponseException;
import server.ServerFacade;

import java.util.Arrays;
import java.util.List;

public class PostloginClient {
    private final ServerFacade server;
    String authToken;

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
            return "joining";
        }
        throw new ResponseException(400, "Expected: <ID> [WHITE|BLACK]");
    }

    public String observe(String...params) throws ResponseException {
        if (params.length >= 1) {
            if (!isInteger(params[0])) {
                throw new ResponseException(400, "ID must be an integer");
            }
            return "observing";
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

    public static boolean isInteger(String s) {
        try {
            Integer.parseInt(s);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }
}
