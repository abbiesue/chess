package websocket.commands;

public class LeaveGameCommand extends UserGameCommand {
    private final CommandType commandType = CommandType.LEAVE;
    private final String authToken;
    private final Integer gameID;

    public LeaveGameCommand(String authToken, Integer gameID) {
        super(CommandType.LEAVE, authToken, gameID);
        this.authToken = authToken;
        this.gameID = gameID;
    }
}
