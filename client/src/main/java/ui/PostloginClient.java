package ui;

import records.*;
import server.ResponseException;
import server.ServerFacade;

import java.util.Arrays;

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
                //case "join" -> join(params);
                //case "observe" -> observe(params);
                case "logout" -> logout();
                case "quit" -> "quit";
                default -> help();
            };
        } catch (ResponseException ex) {
            return ex.getMessage();
        }
    }

    public String create (String... params) throws ResponseException {
        if (params.length >= 1) {
            var gameName = params[0];
            server.create(new CreateRequest(authToken, gameName));
            return "Successfully created " + gameName;
        }
        throw new ResponseException(400, "Expected: <username> <password>");
    }

    public String list () {
        ListResult result;
        return "finish";
    }

    public String logout () throws ResponseException {
        server.logout(new LogoutRequest(authToken));
        return "logged out";
    }

    public String help() {
        return """
                create <NAME> - to create a game
                list - to list games
                join <ID> [WHITE|BLACK] - to join a game
                observe<ID> - to observe a game
                logout - to logout
                quit - to exit program
                help - to list options
                """;
    }
}
