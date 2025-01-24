package chess;

import java.util.*;

/**
 * Represents a single chess piece
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessPiece {
    private PieceType pieceType;
    private ChessGame.TeamColor pieceColor;
    private boolean hasMoved = false;

    public ChessPiece(ChessGame.TeamColor pieceColor, ChessPiece.PieceType type) {
        pieceType = type;
        this.pieceColor = pieceColor;
    }

    /**
     * The various different chess piece options
     */
    public enum PieceType {
        KING,
        QUEEN,
        BISHOP,
        KNIGHT,
        ROOK,
        PAWN
    }

    /**
     * @return Which team this chess piece belongs to
     */
    public ChessGame.TeamColor getTeamColor() {
        return pieceColor;
    }

    /**
     * @return which type of chess piece this piece is
     */
    public PieceType getPieceType() {
        return pieceType;
    }

    /**
     * Calculates all the positions a chess piece can move to
     * Does not take into account moves that are illegal due to leaving the king in
     * danger
     *
     * @return Collection of valid moves
     */
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        // store pieceType
        // call pieceMovesCalculator with the pieceType
        PieceMovesCalculator piece = switch(getPieceType()) {
            case BISHOP -> new BishopMovesCalculator();
            case ROOK -> new RookMovesCalculator();
            case KNIGHT -> new KnightMovesCalculator();
            case KING -> new KingMovesCalculator();
            case QUEEN -> new QueenMovesCalculator();
            default -> throw new IllegalStateException("Unexpected value: " + getPieceType());
        };
        return piece.pieceMoves(board, myPosition);
    }

    @Override
    public String toString() {
        return "ChessPiece{" +
                "pieceType=" + pieceType +
                ", pieceColor=" + pieceColor +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ChessPiece that = (ChessPiece) o;
        return pieceType == that.pieceType && pieceColor == that.pieceColor;
    }

    @Override
    public int hashCode() {
        int colorHash = 0;
        if (pieceColor == ChessGame.TeamColor.BLACK){
            colorHash = 0;
        } else {
            colorHash = 1;
        }
        int typeHash = 0;
        List<PieceType> pieceTypes = new ArrayList<ChessPiece.PieceType>(Arrays.asList(PieceType.PAWN, PieceType.ROOK, PieceType.KNIGHT, PieceType.BISHOP, PieceType.QUEEN, PieceType.QUEEN));
        for (int i = 1; i < pieceTypes.size(); i++) {
            if (pieceType == pieceTypes.get(i)) {
                typeHash = i;
            }
        }
        return (colorHash*31) + typeHash;
    }
}
