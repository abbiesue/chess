package chess;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class RookMovesCalculator implements PieceMovesCalculator {

    @Override
    public Collection<ChessMove> pieceMoves(ChessBoard chessboard, ChessPosition position) {
        List<ChessMove> rookMoves = new ArrayList<>();
        ChessPosition startPosition = position;
        int row;
        int col;

        //check down
        col = startPosition.getColumn();
        row = startPosition.getRow() - 1;
        while (row > 0) {
            position = new ChessPosition(row, col);
            if (chessboard.getPiece(position) != null) {
                if (chessboard.getPiece(position).getTeamColor() != chessboard.getPiece(startPosition).getTeamColor()) {
                    rookMoves.add(new ChessMove(startPosition, position, null));
                }
                break;
            }
            if (chessboard.getPiece(position) == null) {
                rookMoves.add(new ChessMove(startPosition, position, null));
            }
            row--;
        }

        //check left
        col = startPosition.getColumn() - 1;
        row = startPosition.getRow();
        while (col > 0) {
            position = new ChessPosition(row, col);
            if (chessboard.getPiece(position) != null) {
                if (chessboard.getPiece(position).getTeamColor() != chessboard.getPiece(startPosition).getTeamColor()) {
                    rookMoves.add(new ChessMove(startPosition, position, null));
                }
                break;
            }
            if (chessboard.getPiece(position) == null) {
                rookMoves.add(new ChessMove(startPosition, position, null));
            }
            col--;
        }

        //check right
        col = startPosition.getColumn() + 1;
        row = startPosition.getRow();
        while (col < 9) {
            position = new ChessPosition(row, col);
            if (chessboard.getPiece(position) != null) {
                if (chessboard.getPiece(position).getTeamColor() != chessboard.getPiece(startPosition).getTeamColor()) {
                    rookMoves.add(new ChessMove(startPosition, position, null));
                }
                break;
            }
            if (chessboard.getPiece(position) == null) {
                rookMoves.add(new ChessMove(startPosition, position, null));
            }
            col++;
        }

        //check up
        row = startPosition.getRow() + 1;
        col = startPosition.getColumn();
        while (row < 9) {
            position = new ChessPosition(row, col);
            if (chessboard.getPiece(position) != null) {
                if (chessboard.getPiece(position).getTeamColor() != chessboard.getPiece(startPosition).getTeamColor()) {
                    rookMoves.add(new ChessMove(startPosition, position, null));
                }
                break;
            }
            if (chessboard.getPiece(position) == null) {
                rookMoves.add(new ChessMove(startPosition, position, null));
            }
            row++;
        }

        return rookMoves;
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
