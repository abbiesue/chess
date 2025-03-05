package chess;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public interface PieceMovesCalculator {
    List<ChessMove> moves = new ArrayList<>();
    ChessPosition startPosition = new ChessPosition(0,0);
    ChessBoard chessBoard = new ChessBoard();

    Collection<ChessMove> pieceMoves(ChessBoard chessboard, ChessPosition position);
    public static boolean checkNAdd(int row, int col) {
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
