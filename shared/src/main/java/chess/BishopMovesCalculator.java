package chess;

import java.util.Collection;

public class BishopMovesCalculator extends PieceMovesCalculator {
    private Collection<ChessMove> bishopMoves;

    public BishopMovesCalculator() {
        this.bishopMoves = null;
    }

    @Override
    public Collection<ChessMove> pieceMoves(ChessBoard chessboard, ChessPosition position) {
        // finds the positions that the bishop can move to and adds them to the Collection bishopMoves
        return bishopMoves;
    }
}
