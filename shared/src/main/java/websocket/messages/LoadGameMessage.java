package websocket.messages;

import chess.ChessGame;

public class LoadGameMessage extends ServerMessage {
    ServerMessageType type = ServerMessageType.LOAD_GAME;
    ChessGame game;

    public LoadGameMessage(ChessGame game) {
        super(ServerMessageType.LOAD_GAME);
        this.game = game;
    }
}
