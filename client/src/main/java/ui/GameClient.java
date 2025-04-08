package ui;

import server.ResponseException;

public abstract class GameClient {

    //these two don't use the websocket and instead interact with the chessgame and BoardPrinter

    public String highlightLegalMoves(String... params) throws ResponseException {
        // add code to make sure that even if there is the right number of parameters they are the right parameters
        if (params.length < 2) {
            return "did you mean \"highlight legal moves <START_POSITION>\"? Please try again.";
        } else {
            return "highlight legal moves is under construction...";
        }
    }

    public String redrawChessBoard(String... params) {
        // add code to make sure that even if there is the right number of parameters they are the right parameters
        if (params.length < 2) {
            return "did you mean \"redraw chess board\"? Please try again.";
        } else {
            return "redraw chess board is under construction...";
        }
    }
}
