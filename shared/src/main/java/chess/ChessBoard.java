package chess;

import java.util.Arrays;

/**
 * A chessboard that can hold and rearrange chess pieces.
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessBoard implements Cloneable{
    private ChessPiece[][] squares;
    public ChessBoard() {
        squares = new ChessPiece[9][9];
    }


    /**
     * Adds a chess piece to the chessboard
     *
     * @param position where to add the piece to
     * @param piece    the piece to add
     */
    public void addPiece(ChessPosition position, ChessPiece piece) {
        squares[position.getRow()][position.getColumn()] = piece;
    }

    /**
     * Gets a chess piece on the chessboard
     *
     * @param position The position to get the piece from
     * @return Either the piece at the position, or null if no piece is at that
     * position
     */
    public ChessPiece getPiece(ChessPosition position) {
        return squares[position.getRow()][position.getColumn()];
    }

    /**
     * Sets the board to the default starting board
     * (How the game of chess normally starts)
     */
    public void resetBoard() {
        squares = new ChessPiece[9][9];
        squares[8][1] = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.ROOK);
        squares[8][2] = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.KNIGHT);
        squares[8][3] = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.BISHOP);
        squares[8][4] = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.QUEEN);
        squares[8][5] = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.KING);
        squares[8][6] = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.BISHOP);
        squares[8][7] = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.KNIGHT);
        squares[8][8] = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.ROOK);
        for (int i = 1; i < 9; i++) {
            squares[7][i] = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.PAWN);
        }
        squares[1][1] = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.ROOK);
        squares[1][2] = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.KNIGHT);
        squares[1][3] = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.BISHOP);
        squares[1][4] = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.QUEEN);
        squares[1][5] = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.KING);
        squares[1][6] = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.BISHOP);
        squares[1][7] = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.KNIGHT);
        squares[1][8] = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.ROOK);
        for (int i = 1; i < 9; i++) {
            squares[2][i] = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.PAWN);
        }

    }

    @Override
    public String toString() {
        String outString =  "ChessBoard: \n";

        for (int i = 1; i < 9; i++) {
            for (int j = 1; j < 9; j++) {
                ChessPiece temp = squares[i][j];
                if (temp == null) {
                    outString = outString.concat(" ");
                } else {
                    switch(temp.getPieceType()) {
                        case PAWN -> {
                            if (temp.getTeamColor() == ChessGame.TeamColor.BLACK) {
                                outString = outString.concat("P");
                            } else { outString = outString.concat("p");}
                        }
                        case ROOK -> {
                            if (temp.getTeamColor() == ChessGame.TeamColor.BLACK) {
                                outString = outString.concat("R");
                            } else { outString = outString.concat("r");}
                        }
                        case KNIGHT -> {
                            if (temp.getTeamColor() == ChessGame.TeamColor.BLACK) {
                                outString = outString.concat("N");
                            } else { outString = outString.concat("n");}
                        }
                        case BISHOP -> {
                            if (temp.getTeamColor() == ChessGame.TeamColor.BLACK) {
                                outString = outString.concat("B");
                            } else { outString = outString.concat("b");}
                        }
                        case QUEEN -> {
                            if (temp.getTeamColor() == ChessGame.TeamColor.BLACK) {
                                outString = outString.concat("Q");
                            } else { outString = outString.concat("q");}
                        }
                        case KING -> {
                            if (temp.getTeamColor() == ChessGame.TeamColor.BLACK) {
                                outString = outString.concat("K");
                            } else { outString = outString.concat("k");}
                        }
                    }
                }
                outString = outString.concat(" | ");
            }
            outString = outString.concat("\n");
        }

        return outString;
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ChessBoard that = (ChessBoard) o;
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (squares[i][j] == that.squares[i][j]) {
                    break;
                }
                if (squares[i][j].getTeamColor() != that.squares[i][j].getTeamColor()) {
                    return false;
                }
                if (squares[i][j].getPieceType() != that.squares[i][j].getPieceType()){
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(squares);
    }

    @Override
    public ChessBoard clone() {
        ChessBoard clone = new ChessBoard();
        ChessPiece[][] cloneBoard = new ChessPiece[9][9];
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (squares[i][j] != null) {
                    ChessPiece pieceClone = squares[i][j].clone();
                    cloneBoard[i][j] = pieceClone;
                }
            }
        }
        clone.squares = cloneBoard;
        return clone;
    }
}
