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

    public boolean checkNAdd(int row, int col) {
        ChessPosition position = new ChessPosition(row, col);
        if (chessBoard.getPiece(position) != null) {
            if (chessBoard.getPiece(position).getTeamColor() != chessBoard.getPiece(startPosition).getTeamColor()) {
                moves.add(new ChessMove(startPosition, position, null));
            }
            return false;
        }
        if (chessBoard.getPiece(position) == null) {
            moves.add(new ChessMove(startPosition, position, null));
        }
        return true;
    }
}
