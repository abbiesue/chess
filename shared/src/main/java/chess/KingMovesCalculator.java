package chess;

import java.util.Collection;

public class KingMovesCalculator extends PieceMovesCalculator {
    private Collection<ChessMove> kingMoves;

    public KingMovesCalculator() {
        this.kingMoves = null;
    }

    @Override
    public Collection<ChessMove> pieceMoves(ChessBoard chessboard, ChessPosition position) {

        return kingMoves;
    }
}
