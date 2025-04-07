package websocket.commands;

public class ConnectCommand extends UserGameCommand{
    private final CommandType commandType = CommandType.CONNECT;
    private final String authToken;
    private final Integer gameID;

    public ConnectCommand(String authToken, Integer gameID) {
        super(CommandType.CONNECT, authToken, gameID);
        this.authToken = authToken;
        this.gameID = gameID;
    }
}
