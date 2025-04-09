package chess;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class KnightMovesCalculator implements PieceMovesCalculator{
    List<ChessMove> moves = new ArrayList<>();
    ChessPosition startPosition;
    ChessBoard chessBoard = new ChessBoard();

    @Override
    public Collection<ChessMove> pieceMoves(ChessBoard chessboard, ChessPosition position) {
        startPosition = position;
        chessBoard = chessboard;
        int row = startPosition.getRow();
        int col = startPosition.getColumn();

        //check up-right (row+2, col+1)
        if (row+2 < 9 && col+1 < 9) {
            callCheckNAdd(row+2, col+1);
        }
        //check right-up (row+1, col+2)
        if (row+1 < 9 && col+2 < 9) {
            callCheckNAdd(row+1, col+2);
        }
        //check right-down (row-1, col+2)
        if (row-1 > 0 && col+2 < 9) {
            callCheckNAdd(row-1, col+2);
        }
        //check down-right (row-2, col+1)
        if (row-2 > 0 && col+1 < 9) {
            callCheckNAdd(row-2, col+1);
        }
        //check down-left (row-2, col-1)
        if (row-2 > 0 && col-1 > 0) {
            callCheckNAdd(row-2, col-1);
        }
        //check left-down (row-1, col-2)
        if (row-1 > 0 && col-2 > 0) {
            callCheckNAdd(row-1, col-2);
        }
        //check left-up (row+1, col-2)
        if (row+1 < 9 && col-2 > 0) {
            callCheckNAdd(row+1, col-2);
        }
        //check up-left (row+2, col-1)
        if (row+2 < 9 && col-1 > 0) {
            callCheckNAdd(row+2, col-1);
        }
        return moves;
    }

    private boolean callCheckNAdd(int row, int col) {
        return checkNAdd(row, col, moves, startPosition, chessBoard);
    }
}
