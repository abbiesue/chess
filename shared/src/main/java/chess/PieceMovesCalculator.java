package chess;

import java.util.Collection;
import java.util.List;

public interface PieceMovesCalculator {

    Collection<ChessMove> pieceMoves(ChessBoard chessboard, ChessPosition position);

    public default boolean checkNAdd(int row, int col, List<ChessMove> moves, ChessPosition startPosition, ChessBoard chessBoard) {
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
