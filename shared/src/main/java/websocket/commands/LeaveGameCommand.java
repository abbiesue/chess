package websocket.commands;

public class LeaveGameCommand extends UserGameCommand {
    private final UserGameCommand.CommandType commandType;
    private final String authToken;
    private final Integer gameID;

    public LeaveGameCommand(UserGameCommand.CommandType commandType, String authToken, Integer gameID) {
        super(commandType, authToken, gameID);
        this.authToken = authToken;
        this.commandType = UserGameCommand.CommandType.LEAVE;
        this.gameID = gameID;
    }
}
