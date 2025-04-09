package chess;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class KingMovesCalculator implements PieceMovesCalculator {
    List<ChessMove> moves = new ArrayList<>();
    ChessPosition startPosition;
    ChessBoard chessBoard = new ChessBoard();

    @Override
    public Collection<ChessMove> pieceMoves(ChessBoard chessboard, ChessPosition position) {
        startPosition = position;
        chessBoard = chessboard;
        int row = startPosition.getRow();
        int col = startPosition.getColumn();
        //check (row+1, col)

        if (row+1 < 9) {
            callCheckNAdd(row+1,col);
        }

        //check (row+1, col+1)
        if (row+1 < 9 && col+1 < 9) {
            callCheckNAdd(row+1,col+1);
        }
        //check (row, col+1)
        if (col+1 < 9) {
            callCheckNAdd(row,col+1);
        }
        //check (row-1, col+1)
        if (row-1 > 0 && col+1 < 9) {
            callCheckNAdd(row-1,col+1);
        }
        //check (row-1, col)
        if (row-1 > 0) {
            callCheckNAdd(row-1,col);
        }
        //check (row-1, col-1)
        if (row-1 > 0 && col-1 > 0) {
            callCheckNAdd(row-1,col-1);
        }
        //check (row, col-1)
        if (col-1 > 0) {
            callCheckNAdd(row,col-1);
        }
        //check (row+1, col-1)
        if (row+1 < 9 && col-1 > 0) {
            callCheckNAdd(row+1,col-1);
        }

        return moves;
    }

    private boolean callCheckNAdd(int row, int col) {
        return checkNAdd(row, col, moves, startPosition, chessBoard);
    }
}
