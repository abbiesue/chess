package chess;

import java.util.Collection;

public abstract class PieceMovesCalculator {
    private ChessBoard chessBoard;
    private ChessPosition position;
    private ChessPiece.PieceType pieceType;

    //add a constructor that assigns all local variables
    //connect to the correct child class based on the piece type given. so the constructor needs to take in the piece type.
    public abstract Collection<ChessMove> pieceMoves(ChessBoard chessboard, ChessPosition position);
}
