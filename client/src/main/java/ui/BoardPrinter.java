package ui;

import chess.ChessBoard;
import chess.ChessGame;
import chess.ChessPosition;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

import static ui.EscapeSequences.*;
import static ui.EscapeSequences.SET_TEXT_COLOR_BRIGHT_RED;

public class BoardPrinter {
    static final int BOARD_SIZE_IN_SQUARES = 8;
    static final int TOTAL_COLUMNS = 8;
    static final int TOTAL_ROWS = 8;
    static final String LIGHT = "light";
    static final String DARK = "dark";
    static final String WHITE = "WHITE";

    private PrintStream out;
    private String playerColor;

    public void printFromGame(ChessGame game, String printColor){
        ChessBoard board = game.getBoard();
        out = new PrintStream(System.out, true, StandardCharsets.UTF_8);
        this.playerColor = printColor;
        if (playerColor.equals(WHITE)){
            game.getBoard().flipBoard();
        }
        String shade = DARK;
        int rowNumber;
        String rowHeader = "";

        printHorizontalBoarder();
        for (int row = 1; row <= TOTAL_ROWS; row++) {
            if (playerColor.equals(WHITE)) {rowNumber = (TOTAL_ROWS+1) - row;} else {rowNumber = row;}
            out.print("\n");
            rowHeader = rowHeader.concat(" " + rowNumber + " ");
            printBoarderSquare(rowHeader);
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
                printBoardSquare(printChar, shade);
                if (shade.equals(LIGHT)) {shade = DARK;} else {shade = LIGHT;}
            }
            printBoarderSquare(rowHeader);
            out.print(SET_BG_COLOR_DARK_GREY);
            if (shade.equals(LIGHT)) {shade = DARK;} else {shade = LIGHT;}
            rowHeader = "";
        }
        printHorizontalBoarder();
    }

    private void printHorizontalBoarder() {
        out.print("\n");
        setGrey();
        String[] headers;
        if (playerColor.equals(WHITE)) {
            headers = new String[]{ "   ", " a ", " b ", " c ", " d ", " e ", " f ", " g ", " h ", "   "};
        } else {
            headers = new String[]{ "   ", " h ", " g ", " f ", " e ", " d ", " c ", " b ", " a ", "   "};
        }
        for (int col = 0; col < BOARD_SIZE_IN_SQUARES + 2; col++) {
            printBoarderSquare(headers[col]);
        }
        out.print(SET_BG_COLOR_DARK_GREY);
    }

    private void printBoarderSquare(String character){
        setGrey();
        out.print(SET_BG_COLOR_DARK_GREEN);
        out.print(SET_TEXT_COLOR_BLACK);
        out.print(character);
    }

    private void printBoardSquare(String piece, String shade) {
        if (shade.equals(DARK)) {
            setDark();
        } else {
            setLight();
        }
        out.print(SET_TEXT_COLOR_BLACK);
        out.print(piece);
    }

    private void setGrey() {
        out.print(SET_BG_COLOR_LIGHT_GREY);
        out.print(SET_TEXT_COLOR_LIGHT_GREY);
    }

    private void setDark() {
        out.print(SET_BG_COLOR_DARK_RED);
        out.print(SET_TEXT_COLOR_DARK_RED);
    }

    private void setLight() {
        out.print(SET_BG_COLOR_BRIGHT_RED);
        out.print(SET_TEXT_COLOR_BRIGHT_RED);
    }

}
