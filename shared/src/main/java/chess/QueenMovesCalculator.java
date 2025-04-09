package chess;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class QueenMovesCalculator implements PieceMovesCalculator{
    List<ChessMove> moves = new ArrayList<>();
    ChessPosition startPosition;
    ChessBoard chessBoard;

    @Override
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition position) {
        moves = new ArrayList<>();
        chessBoard = board;
        startPosition = position;
        int row;
        int col;

        //check up
        row = startPosition.getRow() + 1;
        col = startPosition.getColumn();
        while (row < 9) {
            if (!callCheckNAdd(row,col)) {
                break;
            }
            row++;
        }
        //check up right
        col = startPosition.getColumn()+1;
        row = startPosition.getRow()+1;
        while (row <= 8 && col <= 8) {
            if (!callCheckNAdd(row,col)) {
                break;
            }
            row++;
            col++;
        }
        //check right
        col = startPosition.getColumn() + 1;
        row = startPosition.getRow();
        while (col < 9) {
            if (!callCheckNAdd(row,col)) {
                break;
            }
            col++;
        }
        //check down right
        col = startPosition.getColumn()+1;
        row = startPosition.getRow()-1;
        while (row > 0 && col <= 8) {
            //set new ChessPosition
            if (!callCheckNAdd(row,col)) {
                break;
            }
            row--;
            col++;
        }
        //check down
        col = startPosition.getColumn();
        row = startPosition.getRow() - 1;
        while (row > 0) {
            if (!callCheckNAdd(row,col)) {
                break;
            }
            row--;
        }
        //check down left
        col = startPosition.getColumn()-1;
        row = startPosition.getRow()-1;
        while (row > 0 && col > 0) {
            if (!callCheckNAdd(row,col)) {
                break;
            }
            row--;
            col--;
        }
        //check left
        col = startPosition.getColumn() - 1;
        row = startPosition.getRow();
        while (col > 0) {
            if (!callCheckNAdd(row,col)) {
                break;
            }
            col--;
        }
        //check up left
        col = startPosition.getColumn()-1;
        row = startPosition.getRow()+1;
        while (row <= 8 && col > 0) {
            if (!callCheckNAdd(row,col)) {
                break;
            }
            row++;
            col--;
        }
        return moves;
    }
    private boolean callCheckNAdd(int row, int col) {
        return checkNAdd(row, col, moves, startPosition, chessBoard);
    }

}
