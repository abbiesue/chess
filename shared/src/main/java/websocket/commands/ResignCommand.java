package websocket.commands;

public class ResignCommand extends UserGameCommand {
    private final CommandType commandType = CommandType.RESIGN;
    private final String authToken;
    private final Integer gameID;

    public ResignCommand(String authToken, Integer gameID) {
        super(CommandType.RESIGN, authToken, gameID);
        this.authToken = authToken;
        this.gameID = gameID;
    }
}
