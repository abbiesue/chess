package chess;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class BishopMovesCalculator implements PieceMovesCalculator {
    List<ChessMove> moves = new ArrayList<>();
    ChessPosition startPosition;
    ChessBoard chessBoard = new ChessBoard();

    @Override
    public Collection<ChessMove> pieceMoves(ChessBoard chessboard, ChessPosition position) {
        // initialized local variables
        startPosition = position;
        chessBoard = chessboard;
        // four directions to go: up and right, up and left, down and left, down and right

        //check up and right to the edge of the board
        int col = startPosition.getColumn()+1;
        int row = startPosition.getRow()+1;
        while (row <= 8 && col <= 8) {
            if (checkNAdd(row,col)) {
                row++;
                col++;
            } else {
                break;
            }
            //increment both row and col
        }

        //check down and right to the edge of the board
        col = startPosition.getColumn()+1;
        row = startPosition.getRow()-1;
        while (row > 0 && col <= 8) {
            if (checkNAdd(row,col)) {
                row--;
                col++;
            } else {
                break;
            }
        }

        //check down and left to the edge of the board
        col = startPosition.getColumn()-1;
        row = startPosition.getRow()-1;
        while (row > 0 && col > 0) {
            if (checkNAdd(row,col)) {
                row--;
                col--;
            } else {
                break;
            }
        }

        //check up and left to the edge of the board
        col = startPosition.getColumn()-1;
        row = startPosition.getRow()+1;
        while (row <= 8 && col > 0) {
            if (checkNAdd(row,col)) {
                row++;
                col--;
            } else {
                break;
            }
        }
        return moves;
    }

    @Override
    public boolean checkNAdd(int row, int col) {
        //set new ChessPosition
        ChessPosition position = new ChessPosition(row, col);
        // if there is a piece at this space
        if (chessBoard.getPiece(position) == null) {
            //if the space is free, add it to the collection
            moves.add(new ChessMove(startPosition, position, null));
        }
        if (chessBoard.getPiece(position) != null) {
            // and the piece is not on my team, add to array and break while loop
            if (chessBoard.getPiece(position).getTeamColor() != chessBoard.getPiece(startPosition).getTeamColor()) {
                moves.add(new ChessMove(startPosition, position, null));
            }
            //else, I'm blocked.
            return false;
        }
        return true;
    }
}
