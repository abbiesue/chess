package ui;

import records.*;
import server.ResponseException;
import server.ServerFacade;

import java.util.Arrays;

public class PreloginClient {
    private final ServerFacade server;
    private String username;
    String authToken;

    public PreloginClient(ServerFacade server) {
        this.server = server;
    }

    public String eval(String input) {
        try {
            var tokens = input.toLowerCase().split(" ");
            var cmd = (tokens.length > 0) ? tokens[0] : "help";
            var params = Arrays.copyOfRange(tokens, 1, tokens.length);
            return switch (cmd) {
                case "login" -> login(params);
                case "register" -> register(params);
                case "quit" -> "quit";
                default -> help();
            };
        } catch (ResponseException ex) {
            return ex.getMessage();
        }
    }

    public String login(String... params) throws ResponseException {
        if (params.length >= 1) {
            username = params[0];
            LoginResult result = server.login(new LoginRequest(username, params[1]));
            authToken = result.authToken();
            return "Logged in as " + username;
        }
        throw new ResponseException(400, "Expected: <username> <password>");
    }

    public String register(String... params) throws ResponseException {
        if (params.length >= 1) {
            username = params[0];
            String password = params[1];
            String email = params[2];
            RegisterResult result = server.register(new RegisterRequest(username, password, email));
            authToken = result.authToken();
            return "Logged in as " + username;
        }
        throw new ResponseException(400, "Expected: <username> <password> <email>");
    }

    public String help() {
        return """
                register <USERNAME> <PASSWORD> <EMAIL> - to create an account
                login <USERNAME> <PASSWORD> - to access to server
                quit - to exit program
                help - to list options
                """;
    }
}
