package ui;

import records.JoinRequest;
import server.ResponseException;
import server.ServerFacade;

import java.util.Arrays;

public class GameClient {
    private final ServerFacade server;
    String authToken;

    public GameClient(ServerFacade server, String authToken) {
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
            int gameID = Integer.parseInt(params[0]);
            var playerColor = params[1].toUpperCase();
            server.join(new JoinRequest(authToken, playerColor, gameID));
            if (playerColor.equals("WHITE")) {
                return loadWhiteSideBoard();
            }
            return loadBlackSideBoard();
        }
        throw new ResponseException(400, "Expected: <ID> [WHITE|BLACK]");
    }

    public String observe(String...params) throws ResponseException {
        if (params.length >= 1) {
            //int gameID = Integer.parseInt(params[0]);
            //this is hardcoded and will need to be changed later.
            return loadWhiteSideBoard();
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

    private String loadWhiteSideBoard() {
        return "whiteboard";
    }

    private String loadBlackSideBoard(){
        return "blackboard";
    }
}
