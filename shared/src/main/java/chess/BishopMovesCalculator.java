package chess;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class BishopMovesCalculator implements PieceMovesCalculator {

    @Override
    public Collection<ChessMove> pieceMoves(ChessBoard chessboard, ChessPosition position) {
        // finds the positions that the bishop can move to and adds them to the Collection bishopMoves
        // initialized local variables
        List<ChessMove> bishopMoves = new ArrayList<ChessMove>();
        ChessPosition startPosition = position;
        int col = position.getColumn();
        int row = position.getRow();
        // four directions to go: up and right, up and left, down and left, down and right
        //check up and right to the edge of the board
        while (row < 8 && col < 8) {
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
        // reset to startPosition
        return bishopMoves;
    }
}
