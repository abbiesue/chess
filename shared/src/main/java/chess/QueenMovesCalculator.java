package chess;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class QueenMovesCalculator implements PieceMovesCalculator{
    List<ChessMove> queenMoves = new ArrayList<>();
    ChessPosition startPosition;
    ChessBoard chessboard;

    @Override
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition position) {
        queenMoves = new ArrayList<>();
        chessboard = board;
        startPosition = position;
        int row;
        int col;

        //check up
        row = startPosition.getRow() + 1;
        col = startPosition.getColumn();
        while (row < 9) {
            if (!checkNAdd(row,col)) {
                break;
            }
            row++;
        }
        //check up right
        col = startPosition.getColumn()+1;
        row = startPosition.getRow()+1;
        while (row <= 8 && col <= 8) {
            if (!checkNAdd(row,col)) {
                break;
            }
            row++;
            col++;
        }
        //check right
        col = startPosition.getColumn() + 1;
        row = startPosition.getRow();
        while (col < 9) {
            if (!checkNAdd(row,col)) {
                break;
            }
            col++;
        }
        //check down right
        col = startPosition.getColumn()+1;
        row = startPosition.getRow()-1;
        while (row > 0 && col <= 8) {
            //set new ChessPosition
            if (!checkNAdd(row,col)) {
                break;
            }
            row--;
            col++;
        }
        //check down
        col = startPosition.getColumn();
        row = startPosition.getRow() - 1;
        while (row > 0) {
            if (!checkNAdd(row,col)) {
                break;
            }
            row--;
        }
        //check down left
        col = startPosition.getColumn()-1;
        row = startPosition.getRow()-1;
        while (row > 0 && col > 0) {
            if (!checkNAdd(row,col)) {
                break;
            }
            row--;
            col--;
        }
        //check left
        col = startPosition.getColumn() - 1;
        row = startPosition.getRow();
        while (col > 0) {
            if (!checkNAdd(row,col)) {
                break;
            }
            col--;
        }
        //check up left
        col = startPosition.getColumn()-1;
        row = startPosition.getRow()+1;
        while (row <= 8 && col > 0) {
            if (!checkNAdd(row,col)) {
                break;
            }
            row++;
            col--;
        }
        return queenMoves;
    }

    @Override
    public boolean checkNAdd(int row, int col) {
        //set new ChessPosition
        ChessPosition position = new ChessPosition(row, col);
        if (chessboard.getPiece(position) != null) {
            // and the piece is not on my team, add to array and break while loop
            if (chessboard.getPiece(position).getTeamColor() != chessboard.getPiece(startPosition).getTeamColor()) {
                queenMoves.add(new ChessMove(startPosition, position, null));
            }
            //else, I'm blocked.
            return false;
        }
        if (chessboard.getPiece(position) == null) {
            //if the space is free, add it to the collection
            queenMoves.add(new ChessMove(startPosition, position, null));
        }
        return true;
    }

}
