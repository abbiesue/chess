package chess;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class RookMovesCalculator implements PieceMovesCalculator {
    List<ChessMove> moves = new ArrayList<>();
    ChessPosition startPosition;
    ChessBoard chessBoard = new ChessBoard();

    @Override
    public Collection<ChessMove> pieceMoves(ChessBoard chessboard, ChessPosition position) {
        startPosition = position;
        chessBoard = chessboard;
        int row;
        int col;

        //check down
        col = startPosition.getColumn();
        row = startPosition.getRow() - 1;
        while (row > 0) {
            if (checkNAdd(row,col)) {
                row--;
            } else {
                break;
            }
        }

        //check left
        col = startPosition.getColumn() - 1;
        row = startPosition.getRow();
        while (col > 0) {
            if (checkNAdd(row,col)) {
                col--;
            } else {
                break;
            }
        }

        //check right
        col = startPosition.getColumn() + 1;
        row = startPosition.getRow();
        while (col < 9) {
            if (checkNAdd(row,col)) {
                col++;
            } else {
                break;
            }
        }

        //check up
        row = startPosition.getRow() + 1;
        col = startPosition.getColumn();
        while (row < 9) {
            if (checkNAdd(row,col)) {
                row++;
            } else {
                break;
            }
        }

        return moves;
    }

    @Override
    public boolean checkNAdd(int row, int col) {
        //set new ChessPosition
        ChessPosition position = new ChessPosition(row, col);
        // if there is a piece at this space
        if (chessBoard.getPiece(position) == null) {
            //if the space is free, add it to the collection
            moves.add(new ChessMove(startPosition, position, null));
        }
        if (chessBoard.getPiece(position) != null) {
            // and the piece is not on my team, add to array and break while loop
            if (chessBoard.getPiece(position).getTeamColor() != chessBoard.getPiece(startPosition).getTeamColor()) {
                moves.add(new ChessMove(startPosition, position, null));
            }
            //else, I'm blocked.
            return false;
        }
        return true;
    }
}
