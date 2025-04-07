package websocket.commands;

import chess.ChessMove;

public class MakeMoveCommand extends UserGameCommand {
    private final CommandType commandType = CommandType.MAKE_MOVE;
    private final String authToken;
    private final Integer gameID;
    private ChessMove move;

    public MakeMoveCommand(String authToken, Integer gameID, ChessMove move) {
        super(CommandType.MAKE_MOVE, authToken, gameID);
        this.authToken = authToken;
        this.gameID = gameID;
        this.move = move;
    }
}
