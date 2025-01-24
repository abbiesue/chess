package chess;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class BishopMovesCalculator implements PieceMovesCalculator {

    @Override
    public Collection<ChessMove> pieceMoves(ChessBoard chessboard, ChessPosition position) {
        // initialized local variables
        List<ChessMove> bishopMoves = new ArrayList<ChessMove>();
        ChessPosition startPosition = position;
        // four directions to go: up and right, up and left, down and left, down and right

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
                   bishopMoves.add(new ChessMove(startPosition, position, null));
               }
               //else, I'm blocked.
               break;
            }
            if (chessboard.getPiece(position) == null) {
                //if the space is free, add it to the collection
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
                    bishopMoves.add(new ChessMove(startPosition, position, null));
                }
                //else, I'm blocked.
                break;
            }
            if (chessboard.getPiece(position) == null) {
                //if the space is free, add it to the collection
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
                    bishopMoves.add(new ChessMove(startPosition, position, null));
                }
                //else, I'm blocked.
                break;
            }
            if (chessboard.getPiece(position) == null) {
                //if the space is free, add it to the collection
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
                    bishopMoves.add(new ChessMove(startPosition, position, null));
                }
                //else, I'm blocked.
                break;
            }
            if (chessboard.getPiece(position) == null) {
                //if the space is free, add it to the collection
                bishopMoves.add(new ChessMove(startPosition, position, null));
            }
            row++;
            col--;
        }
        return bishopMoves;
    }
}
