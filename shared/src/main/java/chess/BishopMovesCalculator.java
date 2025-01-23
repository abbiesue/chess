package chess;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class BishopMovesCalculator implements PieceMovesCalculator {
    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    /**
     * Returns an array of all possible moves for a bishop at a specified position.
     *
     * @param chessboard the chessboard of the game in progress.
     * @param postion the position the bishop is at.
     * @return the array bishopMoves that is now loaded with potential moves.
     */
    public Collection<ChessMove> pieceMoves(ChessBoard chessboard, ChessPosition position) {
        // initialized local variables
        List<ChessMove> bishopMoves = new ArrayList<ChessMove>();
        ChessPosition startPosition = position;
        int col = startPosition.getColumn();
        int row = startPosition.getRow();
        // four directions to go: up and right, up and left, down and left, down and right

        //check up and right to the edge of the board
        while (row <= 8 && col <= 8) {
            //set new ChessPosition
            position = new ChessPosition(row, col);
            if (chessboard.getPiece(position) == null) {
                //if the space is free, add it to the collection
                position = new ChessPosition(row, col);
                bishopMoves.add(new ChessMove(startPosition, position, null));
            }
            //increment both row and col
            row++;
            col++;
        }
        //reset row and col
        col = startPosition.getColumn();
        row = startPosition.getRow();

        //check down and right to the edge of the board
        while (row > 0 && col <= 8) {
            //set new ChessPosition
            position = new ChessPosition(row, col);
            if (chessboard.getPiece(position) == null) {
                //if the space is free, add it to the collection
                position = new ChessPosition(row, col);
                bishopMoves.add(new ChessMove(startPosition, position, null));
            }
            row--;
            col++;
        }

        //reset row and col
        col = startPosition.getColumn();
        row = startPosition.getRow();

        //check down and left to the edge of the board
        while (row > 0 && col > 0) {
            //set new ChessPosition
            position = new ChessPosition(row, col);
            if (chessboard.getPiece(position) == null) {
                //if the space is free, add it to the collection
                position = new ChessPosition(row, col);
                bishopMoves.add(new ChessMove(startPosition, position, null));
            }
            row--;
            col--;
        }

        //reset row and col
        col = startPosition.getColumn();
        row = startPosition.getRow();

        //check up and left to the edge of the board
        while (row <= 8 && col > 0) {
            //set new ChessPosition
            position = new ChessPosition(row, col);
            if (chessboard.getPiece(position) == null) {
                //if the space is free, add it to the collection
                position = new ChessPosition(row, col);
                bishopMoves.add(new ChessMove(startPosition, position, null));
            }
            row++;
            col--;
        }
        return bishopMoves;
    }
}
