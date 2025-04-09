package websocket.messages;

import chess.ChessGame;

public class LoadGameMessage extends ServerMessage {
    ChessGame game;
    ChessGame.TeamColor printColor;

    public LoadGameMessage(ChessGame game, ChessGame.TeamColor printColor) {
        super(ServerMessageType.LOAD_GAME);
        this.game = game;
        this.printColor = printColor;
    }

    public ChessGame getGame() {
        return game;
    }

    public ChessGame.TeamColor getPrintColor() {
        return printColor;
    }
}
