package ui;

import records.LoginRequest;
import records.RegisterRequest;
import server.ResponseException;
import server.ServerFacade;

import java.util.Arrays;

public class PreloginClient {
    private final ServerFacade server;
    private final String serverUrl;
    private String username;
    private String password;
    private String email;

    public PreloginClient(String serverUrl) {
        server = new ServerFacade(serverUrl);
        this.serverUrl = serverUrl;
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
            server.login(new LoginRequest(username, params[1]));
            return String.format("Logged in as " + username);
        }
        throw new ResponseException(400, "Expected: <username> <password>");
    }

    public String register(String... params) throws ResponseException {
        if (params.length >= 1) {
            username = params[0];
            password = params[1];
            email = params[2];
            server.register(new RegisterRequest(username, password, email));
            return String.format("Logged in as " + username);
        }
        throw new ResponseException(400, "Expected: <username> <password> <email>");
    }

    public String help(String... params) {
        return """
                register <USERNAME> <PASSWORD> <EMAIL> - to create an account
                login <USERNAME> <PASSWORD> - to access to server
                quit - to exit program
                help - to list options
                """;
    }
}
