package ui;

import server.ResponseException;

public abstract class GameClient {

    //these two don't use the websocket and instead interact with the chessgame and BoardPrinter

    public String highlightLegalMoves(String... params) throws ResponseException {
        if (params.length < 2) {
            return "did you mean \"highlight legal moves <START_POSITION>\"? Please try again.";
        } else if (params.length < 3) {
            throw new ResponseException(400, "Expected: highlight legal moves <START_POSITION>");
        } else if (params[2].length() != 2) {
            throw new ResponseException(400, "Expected: <START_POSITION> in form <letter#> ex: E2 or A8");
        } else if (!Character.isAlphabetic(params[2].charAt(0)) || !Character.isDigit(params[2].charAt(1))) {
            throw new ResponseException(400, "Expected: <START_POSITION> in form <letter#> ex: E2 or A8");
        } else {
            //implement highlight legal moves
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
