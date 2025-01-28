package chess;

import java.util.Collection;

public interface PieceMovesCalculator {
    Collection<ChessMove> pieceMoves(ChessBoard chessboard, ChessPosition position);
    boolean checkNAdd(int row, int col);
}
