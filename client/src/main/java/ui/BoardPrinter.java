package ui;

import chess.*;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

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

    public void printFromGame(ChessGame game, ChessGame.TeamColor printColor, List<ChessMove> validMoves) {
        ChessBoard board = game.getBoard();
        out = new PrintStream(System.out, true, StandardCharsets.UTF_8);
        this.playerColor = (printColor == ChessGame.TeamColor.WHITE) ? WHITE : "BLACK";

        String shade = LIGHT;
        printHorizontalBoarder();

        for (int row = 1; row <= TOTAL_ROWS; row++) {
            int actualRow = playerColor.equals(WHITE) ? (TOTAL_ROWS + 1 - row) : row;
            String rowLabel = " " + actualRow + " ";

            out.print("\n");
            printBoarderSquare(rowLabel);

            for (int col = 1; col <= TOTAL_COLUMNS; col++) {
                int actualCol = playerColor.equals(WHITE) ? col : (TOTAL_COLUMNS + 1 - col);

                ChessPosition position = new ChessPosition(actualRow, actualCol);

                String pieceChar = getPieceChar(board.getPiece(position));
                boolean isHighlight = isHighlighted(position, validMoves);

                if (isHighlight) {
                    printHighlightedSquare(pieceChar, shade);
                } else {
                    printBoardSquare(pieceChar, shade);
                }

                shade = toggleShade(shade);
            }

            printBoarderSquare(rowLabel);
            out.print(SET_BG_COLOR_DARK_GREY);
            shade = toggleShade(shade);
        }
        printHorizontalBoarder();
    }


    private String getPieceChar(ChessPiece piece) {
        if (piece == null) {
            return EMPTY;
        }
        return switch (piece.getPieceType()) {
            case ROOK -> (piece.getTeamColor() == ChessGame.TeamColor.WHITE) ? WHITE_ROOK : BLACK_ROOK;
            case KNIGHT -> (piece.getTeamColor() == ChessGame.TeamColor.WHITE) ? WHITE_KNIGHT : BLACK_KNIGHT;
            case BISHOP -> (piece.getTeamColor() == ChessGame.TeamColor.WHITE) ? WHITE_BISHOP : BLACK_BISHOP;
            case KING -> (piece.getTeamColor() == ChessGame.TeamColor.WHITE) ? WHITE_KING : BLACK_KING;
            case QUEEN -> (piece.getTeamColor() == ChessGame.TeamColor.WHITE) ? WHITE_QUEEN : BLACK_QUEEN;
            case PAWN -> (piece.getTeamColor() == ChessGame.TeamColor.WHITE) ? WHITE_PAWN : BLACK_PAWN;
        };
    }

    private boolean isHighlighted(ChessPosition pos, List<ChessMove> moves) {
        if (moves == null || moves.isEmpty()) {
            return false;
        }

        ChessPosition start = moves.get(0).getStartPosition();
        if (start.equals(pos)) {
            return true;
        }

        return moves.stream().anyMatch(move -> move.getEndPosition().equals(pos));
    }

    private String toggleShade(String shade) {
        return shade.equals(LIGHT) ? DARK : LIGHT;
    }


    private void printHighlightedSquare(String piece, String shade) {
        if (shade.equals(DARK)) {
            setDarkHighlight();
        } else {
            setLightHighlight();
        }
        out.print(SET_TEXT_COLOR_BLACK);
        out.print(piece);
    }

    private void printHorizontalBoarder() {
        out.print("\n");
        setGrey();
        String[] headers;
        if (playerColor.equals(WHITE)) {
            headers = new String[]{ "   ", " A ", " B ", " C ", " D ", " E ", " F ", " G ", " H ", "   "};
        } else {
            headers = new String[]{ "   ", " H ", " G ", " F ", " E ", " D ", " C ", " B ", " A ", "   "};
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

    private void setDarkHighlight() {
        out.print(SET_BG_COLOR_GREEN);
        out.print(SET_TEXT_COLOR_GREEN);
    }

    private void setLightHighlight() {
        out.print(SET_BG_COLOR_LIGHT_GREEN);
        out.print(SET_TEXT_COLOR_LIGHT_GREEN);
    }

}
