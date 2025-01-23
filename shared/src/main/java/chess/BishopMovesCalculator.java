package chess;

import java.util.Collection;

public class BishopMovesCalculator extends PieceMovesCalculator {
    private Collection<ChessMove> bishopMoves;
    private ChessPosition startPosition;

    public BishopMovesCalculator() {
        this.bishopMoves = null;
        this.startPosition = null;
    }

    @Override
    public Collection<ChessMove> pieceMoves(ChessBoard chessboard, ChessPosition position) {
        // finds the positions that the bishop can move to and adds them to the Collection bishopMoves
        // initialized local variables
        startPosition = position;
        int col = position.getColumn();
        int row = position.getRow();
        // four directions to go: up and right, up and left, down and left, down and right
        //check up and right to the edge of the board
        while (row < 9 && col < 9) {
            //set new ChessPosition
            position = new ChessPosition(row, col);
            if (chessboard.getPiece(position) == null) {
                //if the space is free, add it to the collection
                bishopMoves.add(new ChessMove(startPosition, position, ChessPiece.PieceType.BISHOP));
            }
            //increment both row and col
            row++;
            col++;
        }

        return bishopMoves;
    }
}
