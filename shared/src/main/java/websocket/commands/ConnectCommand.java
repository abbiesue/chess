package websocket.commands;

public class ConnectCommand extends UserGameCommand{
    private final CommandType commandType;
    private final String authToken;
    private final Integer gameID;

    public ConnectCommand(CommandType commandType, String authToken, Integer gameID) {
        super(commandType, authToken, gameID);
        this.authToken = authToken;
        this.commandType = CommandType.CONNECT;
        this.gameID = gameID;
    }
}
