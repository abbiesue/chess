package ui;

import chess.ChessBoard;
import chess.ChessGame;
import chess.ChessPiece;
import chess.ChessPosition;
import model.GameData;
import records.JoinRequest;
import records.ListRequest;
import records.ListResult;
import server.ResponseException;
import server.ServerFacade;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

import static ui.EscapeSequences.*;

public class GameClient {
    static final int BOARD_SIZE_IN_SQUARES = 8;
    static final int TOTAL_COLUMNS = 8;
    static final int TOTAL_ROWS = 8;
    static final int SPACES_ON_BOARD = 64;
    static final String LIGHT = "light";
    static final String DARK = "dark";
    static final String WHITE = "WHITE";


    private final ServerFacade server;
    String authToken;
    String playerColor;

    public GameClient(ServerFacade server, String authToken) {
        this.server = server;
        this.authToken = authToken;
    }

    public String eval(String input) {
        try {
            var tokens = input.toLowerCase().split(" ");
            var cmd = (tokens.length > 0) ? tokens[0] : "help";
            var params = Arrays.copyOfRange(tokens, 1, tokens.length);
            return switch (cmd) {
                case "join" -> join(params);
                case "observe" -> observe(params);
                case "quit" -> "quit";
                default -> help();
            };
        } catch (ResponseException ex) {
            return ex.getMessage();
        }
    }

    public String join(String... params) throws ResponseException {
        if (params.length >= 2) {
            if (!isInteger(params[0])) {
                throw new ResponseException(400, "ID must be an integer");
            }
            int listID = Integer.parseInt(params[0]);
            int gameID = getIDfromList(listID);
            playerColor = params[1].toUpperCase();
            server.join(new JoinRequest(authToken, playerColor, gameID));
            printFromGame(getGame(gameID));
            return " ";

        }
        throw new ResponseException(400, "Expected: <ID> [WHITE|BLACK]");
    }

    public String observe(String...params) throws ResponseException {
        if (params.length >= 1) {
            playerColor = WHITE;
            if (!isInteger(params[0])) {
                throw new ResponseException(400, "ID must be an integer");
            }
            int listID = Integer.parseInt(params[0]);
            int gameID = getIDfromList(listID);
            printFromGame(getGame(gameID));
            return "\n observing...";
        }
        throw new ResponseException(400, "Expected: <ID>");
    }

    public String help() {
        return """
                create <GAME_NAME> - to create a game
                list - to list games
                join <ID> [WHITE|BLACK] - to join a game
                observe<ID> - to observe a game
                logout - to logout
                quit - to exit program
                help - to list options
                """;
    }

    private ChessGame getGame(int gameID) throws ResponseException {
        var games = server.list(new ListRequest(authToken)).games();
        for (int i = 0; i < games.size(); i++) {
            if (games.get(i).gameID() == gameID) {
                return games.get(i).game();
            }
        }
        throw new ResponseException(400, "Game not Found");
    }

    public int getIDfromList(int listID) throws ResponseException {

        ListResult listResult = server.list(new ListRequest(authToken));
        if (listID > listResult.games().size() || listID < 0) {
            return -1;
        }
        GameData game = listResult.games().get(listID-1);
        return game.gameID();
    }

    public static boolean isInteger(String s) {
        try {
            Integer.parseInt(s);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }


    private void printFromGame(ChessGame game){
        ChessBoard board = game.getBoard();
        PrintStream out = new PrintStream(System.out, true, StandardCharsets.UTF_8);
        if (playerColor.equals(WHITE)){
            game.getBoard().flipBoard();
        }
        String shade = DARK;
        int rowHeader;

        printHorizontalBoarder(out);
        for (int row = 1; row <= TOTAL_ROWS; row++) {
            if (playerColor.equals(WHITE)) {rowHeader = (TOTAL_ROWS+1) - row;} else {rowHeader = row;}
            out.print("\n");
            printBoarderSquare(out, String.valueOf(rowHeader));
            for (int col = 1; col <= TOTAL_COLUMNS; col++) {
                var location = new ChessPosition(row, col);
                String printChar = EMPTY;
                if (board.getPiece(location) != null) {
                    var type = board.getPiece(location).getPieceType();
                    var color = board.getPiece(location).getTeamColor();
                    //switch to assign printChar
                    switch (type) {
                        case ROOK -> {
                            if (color == ChessGame.TeamColor.WHITE) {printChar = WHITE_ROOK;} else {printChar = BLACK_ROOK;}
                        }
                        case KNIGHT -> {
                            if (color == ChessGame.TeamColor.WHITE) {printChar = WHITE_KNIGHT;} else {printChar = BLACK_KNIGHT;}
                        }
                        case BISHOP -> {
                            if (color == ChessGame.TeamColor.WHITE) {printChar = WHITE_BISHOP;} else {printChar = BLACK_BISHOP;}
                        }
                        case KING -> {
                            if (color == ChessGame.TeamColor.WHITE) {printChar = WHITE_KING;} else {printChar = BLACK_KING;}
                        }
                        case QUEEN -> {
                            if (color == ChessGame.TeamColor.WHITE) {printChar = WHITE_QUEEN;} else {printChar = BLACK_QUEEN;}
                        }
                        case PAWN -> {
                            if (color == ChessGame.TeamColor.WHITE) {printChar = WHITE_PAWN;} else {printChar = BLACK_PAWN;}
                        }
                    }
                }
                printBoardSquare(out, printChar, shade);
                if (shade.equals(LIGHT)) {shade = DARK;} else {shade = LIGHT;}
            }
            printBoarderSquare(out, String.valueOf(rowHeader));
            out.print(SET_BG_COLOR_DARK_GREY);
            if (shade.equals(LIGHT)) {shade = DARK;} else {shade = LIGHT;}
        }
        printHorizontalBoarder(out);
    }

    private void printHorizontalBoarder(PrintStream out) {
        out.print("\n");
        setGrey(out);
        String[] headers;
        if (playerColor.equals(WHITE)) {
            headers = new String[]{ " ", " a ", " b ", " c ", " d ", " e ", " f ", " g ", " h ", " "};
        } else {
            headers = new String[]{ " ", " h ", " g ", " f ", " e ", " d ", " c ", " b ", " a ", " "};
        }
        for (int col = 0; col < BOARD_SIZE_IN_SQUARES + 2; col++) {
            printBoarderSquare(out, headers[col]);
        }
        out.print(SET_BG_COLOR_DARK_GREY);
    }

    private void printBoarderSquare(PrintStream out, String character){
        setGrey(out);
        out.print(SET_BG_COLOR_LIGHT_GREY);
        out.print(SET_TEXT_COLOR_BLACK);
        out.print(character);
    }

    private void printBoardSquare(PrintStream out, String piece, String shade) {
        if (shade.equals(DARK)) {
            setDark(out);
        } else {
            setLight(out);
        }
        out.print(SET_TEXT_COLOR_BLACK);
        out.print(piece);
    }

    private void setGrey(PrintStream out) {
        out.print(SET_BG_COLOR_LIGHT_GREY);
        out.print(SET_TEXT_COLOR_LIGHT_GREY);
    }

    private void setDark(PrintStream out) {
        out.print(SET_BG_COLOR_DARK_GREEN);
        out.print(SET_TEXT_COLOR_DARK_GREEN);
    }

    private void setLight(PrintStream out) {
        out.print(SET_BG_COLOR_GREEN);
        out.print(SET_TEXT_COLOR_GREEN);
    }
}
