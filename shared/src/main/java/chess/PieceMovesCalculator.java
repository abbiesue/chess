package chess;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public interface PieceMovesCalculator {
    List<ChessMove> MOVES = new ArrayList<>();
    ChessPosition START_POSITION = new ChessPosition(0,0);
    ChessBoard CHESS_BOARD = new ChessBoard();

    Collection<ChessMove> pieceMoves(ChessBoard chessboard, ChessPosition position);
    public static boolean checkNAdd(int row, int col) {
        ChessPosition position = new ChessPosition(row, col);
        if (CHESS_BOARD.getPiece(position) != null) {
            if (CHESS_BOARD.getPiece(position).getTeamColor() != CHESS_BOARD.getPiece(START_POSITION).getTeamColor()) {
                MOVES.add(new ChessMove(START_POSITION, position, null));
            }
            return false;
        }
        if (CHESS_BOARD.getPiece(position) == null) {
            MOVES.add(new ChessMove(START_POSITION, position, null));
        }
        return true;
    }
}
