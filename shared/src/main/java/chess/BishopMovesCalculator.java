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
        // four directions to go: up and right, up and left, down and left, down and right
        //Now, I need to add the fact that there are other pieces on the board. So if you run into a piece in any direction,
        // you cannot move any farther in that direction.
        // If the piece is not on your team, you can take it and replace it in that spot, so that space is a valid move.
        // If it is on your team, that space is not a valid move.

        //check up and right to the edge of the board
        int col = startPosition.getColumn()+1;
        int row = startPosition.getRow()+1;
        while (row <= 8 && col <= 8) {
            //set new ChessPosition
            position = new ChessPosition(row, col);
            // if there is a piece at this space
            if (chessboard.getPiece(position) != null) {
               // and the piece is not on my team, add to array and break while loop
               if (chessboard.getPiece(position).getTeamColor() != chessboard.getPiece(startPosition).getTeamColor()) {
                   position = new ChessPosition(row, col);
                   bishopMoves.add(new ChessMove(startPosition, position, null));
               }
               //else, I'm blocked.
               break;
            }
            if (chessboard.getPiece(position) == null) {
                //if the space is free, add it to the collection
                position = new ChessPosition(row, col);
                bishopMoves.add(new ChessMove(startPosition, position, null));
            }
            //increment both row and col
            row++;
            col++;
        }

        //check down and right to the edge of the board
        col = startPosition.getColumn()+1;
        row = startPosition.getRow()-1;
        while (row > 0 && col <= 8) {
            //set new ChessPosition
            position = new ChessPosition(row, col);
            if (chessboard.getPiece(position) != null) {
                // and the piece is not on my team, add to array and break while loop
                if (chessboard.getPiece(position).getTeamColor() != chessboard.getPiece(startPosition).getTeamColor()) {
                    position = new ChessPosition(row, col);
                    bishopMoves.add(new ChessMove(startPosition, position, null));
                }
                //else, I'm blocked.
                break;
            }
            if (chessboard.getPiece(position) == null) {
                //if the space is free, add it to the collection
                position = new ChessPosition(row, col);
                bishopMoves.add(new ChessMove(startPosition, position, null));
            }
            row--;
            col++;
        }

        //check down and left to the edge of the board
        col = startPosition.getColumn()-1;
        row = startPosition.getRow()-1;
        while (row > 0 && col > 0) {
            //set new ChessPosition
            position = new ChessPosition(row, col);
            if (chessboard.getPiece(position) != null) {
                // and the piece is not on my team, add to array and break while loop
                if (chessboard.getPiece(position).getTeamColor() != chessboard.getPiece(startPosition).getTeamColor()) {
                    position = new ChessPosition(row, col);
                    bishopMoves.add(new ChessMove(startPosition, position, null));
                }
                //else, I'm blocked.
                break;
            }
            if (chessboard.getPiece(position) == null) {
                //if the space is free, add it to the collection
                position = new ChessPosition(row, col);
                bishopMoves.add(new ChessMove(startPosition, position, null));
            }
            row--;
            col--;
        }

        //check up and left to the edge of the board
        col = startPosition.getColumn()-1;
        row = startPosition.getRow()+1;
        while (row <= 8 && col > 0) {
            //set new ChessPosition
            position = new ChessPosition(row, col);
            if (chessboard.getPiece(position) != null) {
                // and the piece is not on my team, add to array and break while loop
                if (chessboard.getPiece(position).getTeamColor() != chessboard.getPiece(startPosition).getTeamColor()) {
                    position = new ChessPosition(row, col);
                    bishopMoves.add(new ChessMove(startPosition, position, null));
                }
                //else, I'm blocked.
                break;
            }
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
