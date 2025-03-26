package ui;

import records.JoinRequest;
import server.ResponseException;
import server.ServerFacade;

import java.io.OutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

import static ui.EscapeSequences.*;

public class GameClient {
    //board dimensions:
    final int SQUARE_SIZE_IN_PADDED_CHARS = 3;
    final int BOARD_SIZE_IN_SQUARES = 8;

    final String LIGHT = "light";
    final String DARK = "dark";


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
            int gameID = Integer.parseInt(params[0]);
            playerColor = params[1].toUpperCase();
            server.join(new JoinRequest(authToken, playerColor, gameID));
            printBoard();
            return " ";

        }
        throw new ResponseException(400, "Expected: <ID> [WHITE|BLACK]");
    }

    public String observe(String...params) throws ResponseException {
        if (params.length >= 1) {
            //int gameID = Integer.parseInt(params[0]);
            //this is hardcoded and will need to be changed later.
            playerColor = "WHITE";
            printBoard();
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

    private void printBoard() {
        var out = new PrintStream(System.out, true, StandardCharsets.UTF_8);
        out.print(ERASE_SCREEN);
        printHorizontalBoarder(out);
        if (playerColor.equals("WHITE")) {
            printBlackSide(out);
            printEmptySquares(out);
            printWhiteSide(out);
        } else {
            printWhiteSide(out);
            printEmptySquares(out);
            printBlackSide(out);
        }
        printHorizontalBoarder(out);
        out.print(SET_BG_COLOR_DARK_GREY);
        out.print(SET_TEXT_COLOR_WHITE);
    }

    private void printHorizontalBoarder(PrintStream out) {
        out.print("\n");
        setGrey(out);
        String[] headers;
        if (playerColor.equals("WHITE")) {
            headers = new String[]{ " ", " a ", " b ", " c ", " d ", " e ", " f ", " g ", " h ", " "};
        } else {
            headers = new String[]{ " ", " h ", " g ", " f ", " e ", " d ", " c ", " b ", " a ", " "};
        }
        for (int col = 0; col < BOARD_SIZE_IN_SQUARES + 2; col++) {
            printBoarderSquare(out, headers[col]);
        }
    }

    private void printBoarderSquare(PrintStream out, String character){
        setGrey(out);
        out.print(SET_BG_COLOR_LIGHT_GREY);
        out.print(SET_TEXT_COLOR_BLACK);
        out.print(character);
    }

    private void printWhiteSide(PrintStream out) {
        if (playerColor.equals("WHITE")) {
            out.print(SET_BG_COLOR_DARK_GREY);
            out.print("\n");
            printBoarderSquare(out, "2");
            printBoardSquare(out, WHITE_PAWN, LIGHT);
            printBoardSquare(out, WHITE_PAWN, DARK);
            printBoardSquare(out, WHITE_PAWN, LIGHT);
            printBoardSquare(out, WHITE_PAWN, DARK);
            printBoardSquare(out, WHITE_PAWN, LIGHT);
            printBoardSquare(out, WHITE_PAWN, DARK);
            printBoardSquare(out, WHITE_PAWN, LIGHT);
            printBoardSquare(out, WHITE_PAWN, DARK);
            printBoarderSquare(out, "2");
            out.print(SET_BG_COLOR_DARK_GREY);
            out.print("\n");
            printBoarderSquare(out, "1");
            printBoardSquare(out, WHITE_ROOK, DARK);
            printBoardSquare(out, WHITE_KNIGHT, LIGHT);
            printBoardSquare(out, WHITE_BISHOP, DARK);
            printBoardSquare(out, WHITE_QUEEN, LIGHT);
            printBoardSquare(out, WHITE_KING, DARK);
            printBoardSquare(out, WHITE_BISHOP, LIGHT);
            printBoardSquare(out, WHITE_KNIGHT, DARK);
            printBoardSquare(out, WHITE_ROOK, LIGHT);
            printBoarderSquare(out, "1");
            out.print(SET_BG_COLOR_DARK_GREY);
        } else {
            out.print(SET_BG_COLOR_DARK_GREY);
            out.print("\n");
            printBoarderSquare(out, "8");
            printBoardSquare(out, WHITE_ROOK, LIGHT);
            printBoardSquare(out, WHITE_KNIGHT, DARK);
            printBoardSquare(out, WHITE_BISHOP, LIGHT);
            printBoardSquare(out, WHITE_QUEEN, DARK);
            printBoardSquare(out, WHITE_KING, LIGHT);
            printBoardSquare(out, WHITE_BISHOP, DARK);
            printBoardSquare(out, WHITE_KNIGHT, LIGHT);
            printBoardSquare(out, WHITE_ROOK, DARK);
            printBoarderSquare(out, "8");
            out.print(SET_BG_COLOR_DARK_GREY);
            out.print("\n");
            printBoarderSquare(out, "7");
            printBoardSquare(out, WHITE_PAWN, DARK);
            printBoardSquare(out, WHITE_PAWN, LIGHT);
            printBoardSquare(out, WHITE_PAWN, DARK);
            printBoardSquare(out, WHITE_PAWN, LIGHT);
            printBoardSquare(out, WHITE_PAWN, DARK);
            printBoardSquare(out, WHITE_PAWN, LIGHT);
            printBoardSquare(out, WHITE_PAWN, DARK);
            printBoardSquare(out, WHITE_PAWN, LIGHT);
            printBoarderSquare(out, "7");
        }
    }

    private void printBlackSide(PrintStream out) {
        if (playerColor.equals("BLACK")) {
            out.print(SET_BG_COLOR_DARK_GREY);
            out.print("\n");
            printBoarderSquare(out, "2");
            printBoardSquare(out, BLACK_PAWN, LIGHT);
            printBoardSquare(out, BLACK_PAWN, DARK);
            printBoardSquare(out, BLACK_PAWN, LIGHT);
            printBoardSquare(out, BLACK_PAWN, DARK);
            printBoardSquare(out, BLACK_PAWN, LIGHT);
            printBoardSquare(out, BLACK_PAWN, DARK);
            printBoardSquare(out, BLACK_PAWN, LIGHT);
            printBoardSquare(out, BLACK_PAWN, DARK);
            printBoarderSquare(out, "2");
            out.print(SET_BG_COLOR_DARK_GREY);
            out.print("\n");
            printBoarderSquare(out, "1");
            printBoardSquare(out, BLACK_ROOK, DARK);
            printBoardSquare(out, BLACK_KNIGHT, LIGHT);
            printBoardSquare(out, BLACK_BISHOP, DARK);
            printBoardSquare(out, BLACK_KING, LIGHT);
            printBoardSquare(out, BLACK_QUEEN, DARK);
            printBoardSquare(out, BLACK_BISHOP, LIGHT);
            printBoardSquare(out, BLACK_KNIGHT, DARK);
            printBoardSquare(out, BLACK_ROOK, LIGHT);
            printBoarderSquare(out, "1");
            out.print(SET_BG_COLOR_DARK_GREY);
        } else {
            out.print(SET_BG_COLOR_DARK_GREY);
            out.print("\n");
            printBoarderSquare(out, "8");
            printBoardSquare(out, BLACK_ROOK, LIGHT);
            printBoardSquare(out, BLACK_KNIGHT, DARK);
            printBoardSquare(out, BLACK_BISHOP, LIGHT);
            printBoardSquare(out, BLACK_QUEEN, DARK);
            printBoardSquare(out, BLACK_KING, LIGHT);
            printBoardSquare(out, BLACK_BISHOP, DARK);
            printBoardSquare(out, BLACK_KNIGHT, LIGHT);
            printBoardSquare(out, BLACK_ROOK, DARK);
            printBoarderSquare(out, "8");
            out.print(SET_BG_COLOR_DARK_GREY);
            out.print("\n");
            printBoarderSquare(out, "7");
            printBoardSquare(out, BLACK_PAWN, DARK);
            printBoardSquare(out, BLACK_PAWN, LIGHT);
            printBoardSquare(out, BLACK_PAWN, DARK);
            printBoardSquare(out, BLACK_PAWN, LIGHT);
            printBoardSquare(out, BLACK_PAWN, DARK);
            printBoardSquare(out, BLACK_PAWN, LIGHT);
            printBoardSquare(out, BLACK_PAWN, DARK);
            printBoardSquare(out, BLACK_PAWN, LIGHT);
            printBoarderSquare(out, "7");
        }
    }

    private void printBoardSquare(PrintStream out, String piece, String shade) {
        if (shade.equals("dark")) {
            setDark(out);
        } else {
            setLight(out);
        }
        out.print(SET_TEXT_COLOR_BLACK);
        out.print(piece);
    }


    private void printEmptySquares(PrintStream out) {
        if (playerColor.equals("WHITE")) {
            String shade = LIGHT;
            for (int j = 6; j > 2; j--) {
                out.print(SET_BG_COLOR_DARK_GREY);
                out.print("\n");
                printBoarderSquare(out, String.valueOf(j));
                for (int i = 0; i < BOARD_SIZE_IN_SQUARES; i++) {
                    if (shade.equals(LIGHT)) {
                        setLight(out);
                        out.print(EMPTY);
                        shade = DARK;
                    } else {
                        setDark(out);
                        out.print(EMPTY);
                        shade = LIGHT;
                    }
                }
                printBoarderSquare(out, String.valueOf(j));
                if (shade.equals(DARK)) {
                    shade = LIGHT;
                } else {
                    shade = DARK;
                }
            }
        } else {
            String shade = LIGHT;
            for (int j = 3; j < 7; j++) {
                out.print(SET_BG_COLOR_DARK_GREY);
                out.print("\n");
                printBoarderSquare(out, String.valueOf(j));
                for (int i = 0; i < BOARD_SIZE_IN_SQUARES; i++) {
                    if (shade.equals(LIGHT)) {
                        setLight(out);
                        out.print(EMPTY);
                        shade = DARK;
                    } else {
                        setDark(out);
                        out.print(EMPTY);
                        shade = LIGHT;
                    }
                }
                printBoarderSquare(out, String.valueOf(j));
                if (shade.equals(DARK)) {
                    shade = LIGHT;
                } else {
                    shade = DARK;
                }
            }
        }
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
