package ui;

import chess.ChessGame;
import chess.ChessMove;
import chess.ChessPosition;
import records.ListRequest;
import server.ResponseException;
import server.ServerFacade;
import websocket.ServerMessageObserver;
import websocket.messages.ErrorMessage;
import websocket.messages.LoadGameMessage;
import websocket.messages.NotificationMessage;
import websocket.messages.ServerMessage;

import java.util.List;
import java.util.Objects;

public abstract class GameClient implements ServerMessageObserver {
    String playerColor;

    //these two don't use the websocket and instead interact with the chessgame and BoardPrinter

    public String highlightLegalMoves(String playerColor, int gameID, ServerFacade server, String authToken, String... params) throws ResponseException {
        if (params.length < 2) {
            return "did you mean \"highlight legal moves <START_POSITION>\"? Please try again.";
        } else if (params.length < 3) {
            throw new ResponseException(400, "Expected: highlight legal moves <START_POSITION>");
        } else if (params[2].length() != 2) {
            throw new ResponseException(400, "Expected: <START_POSITION> in form <letter#> ex: E2 or A8");
        } else if (!Character.isAlphabetic(params[2].charAt(0)) || !Character.isDigit(params[2].charAt(1))) {
            throw new ResponseException(400, "Expected: <START_POSITION> in form <letter#> ex: E2 or A8");
        } else {
            String stringPos = params[2];
            //turn the string into a position
            ChessGame.TeamColor printColor = stringToTeamColor(playerColor);
            ChessPosition position = stringToPosition(stringPos, printColor);
            //get the game from a gameID
            ChessGame game = getGame(gameID, server, authToken);
            List<ChessMove> validMoves = (List<ChessMove>) game.validMoves(position);
            BoardPrinter printer = new BoardPrinter();
            printer.printFromGame(game, printColor, validMoves);
            return "";
        }
    }

    public String redrawChessBoard(String playerColor, int gameID, ServerFacade server, String authToken, String... params) throws ResponseException {
        // add code to make sure that even if there is the right number of parameters they are the right parameters
        if (params.length < 2) {
            return "did you mean \"redraw chess board\"? Please try again.";
        } else {
            ChessGame.TeamColor printColor = stringToTeamColor(playerColor);

            ChessGame game = getGame(gameID, server, authToken);
            BoardPrinter printer = new BoardPrinter();
            printer.printFromGame(game, printColor,null);
            return "";
        }
    }

    @Override
    public void notify(ServerMessage notification) {
        //call different private classes based on message type
        System.out.println("entered notify");
        switch (notification.getServerMessageType()) {
            case LOAD_GAME -> {
                System.out.println("LoadGameMessage received");
                //BoardPrinter printer = new BoardPrinter();
                //printer.printFromGame(((LoadGameMessage)notification).getGame(), stringToTeamColor(playerColor), null);
            }
            case NOTIFICATION -> {
                System.out.println("NotificationMessage received");
                //System.out.println(EscapeSequences.SET_TEXT_COLOR_MAGENTA + ((NotificationMessage)notification).getMessage());
            }
            case ERROR -> {
                System.out.println("ErrorMessage received");
                //System.out.println(EscapeSequences.SET_TEXT_COLOR_RED + ((ErrorMessage)notification).getMessage());
            }
        }
        System.out.print(EscapeSequences.SET_TEXT_COLOR_WHITE + "\n" + ">>> " + EscapeSequences.SET_TEXT_COLOR_GREEN);
    }

    public ChessGame.TeamColor stringToTeamColor(String playerColor) {
        if (Objects.equals(playerColor, "WHITE")) {
            return ChessGame.TeamColor.WHITE;
        } else {
            return ChessGame.TeamColor.BLACK;
        }
    }

    private ChessGame getGame(int gameID, ServerFacade server, String authToken) throws ResponseException {
        var games = server.list(new ListRequest(authToken)).games();
        for (int i = 0; i < games.size(); i++) {
            if (games.get(i).gameID() == gameID) {
                return games.get(i).game();
            }
        }
        throw new ResponseException(400, "Game not Found");
    }

    public ChessPosition stringToPosition(String stringPos, ChessGame.TeamColor playerColor) throws ResponseException {
        char colChar = Character.toUpperCase(stringPos.charAt(0));
        int col;
        int row = stringPos.charAt(1) - '0';
        if (playerColor == ChessGame.TeamColor.WHITE) {
            switch (colChar) {
                case 'A' -> col = 1;
                case 'B' -> col = 2;
                case 'C' -> col = 3;
                case 'D' -> col = 4;
                case 'E' -> col = 5;
                case 'F' -> col = 6;
                case 'G' -> col = 7;
                case 'H' -> col = 8;
                default -> throw new ResponseException(400, "Invalid Position");
            }
            switch (row) {
                case 1 -> row = 8;
                case 2 -> row = 7;
                case 3 -> row = 6;
                case 4 -> row = 5;
                case 5 -> row = 4;
                case 6 -> row = 3;
                case 7 -> row = 2;
                case 8 -> row = 1;
            }
        } else {
            switch (colChar) {
                case 'A' -> col = 8;
                case 'B' -> col = 7;
                case 'C' -> col = 6;
                case 'D' -> col = 5;
                case 'E' -> col = 4;
                case 'F' -> col = 3;
                case 'G' -> col = 2;
                case 'H' -> col = 1;
                default -> throw new ResponseException(400, "Invalid Position");
            }
        }

        return new ChessPosition(row, col);
    }
}
