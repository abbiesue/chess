package websocket.commands;

public class ResignCommand extends UserGameCommand {
    private final CommandType commandType;
    private final String authToken;
    private final Integer gameID;

    public ResignCommand(CommandType commandType, String authToken, Integer gameID) {
        super(commandType, authToken, gameID);
        this.authToken = authToken;
        this.commandType = CommandType.RESIGN;
        this.gameID = gameID;
    }
}
