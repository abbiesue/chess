package chess;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class QueenMovesCalculator implements PieceMovesCalculator{
    @Override
    public Collection<ChessMove> pieceMoves(ChessBoard chessboard, ChessPosition position) {
        List<ChessMove> queenMoves = new ArrayList<>();
        ChessPosition startPosition = position;
        int row;
        int col;

        //check up
        row = startPosition.getRow() + 1;
        col = startPosition.getColumn();
        while (row < 9) {
            position = new ChessPosition(row, col);
            if (chessboard.getPiece(position) != null) {
                if (chessboard.getPiece(position).getTeamColor() != chessboard.getPiece(startPosition).getTeamColor()) {
                    queenMoves.add(new ChessMove(startPosition, position, null));
                }
                break;
            }
            if (chessboard.getPiece(position) == null) {
                queenMoves.add(new ChessMove(startPosition, position, null));
            }
            row++;
        }
        //check up right
        col = startPosition.getColumn()+1;
        row = startPosition.getRow()+1;
        while (row <= 8 && col <= 8) {
            //set new ChessPosition
            position = new ChessPosition(row, col);
            // if there is a piece at this space
            if (chessboard.getPiece(position) != null) {
                // and the piece is not on my team, add to array and break while loop
                if (chessboard.getPiece(position).getTeamColor() != chessboard.getPiece(startPosition).getTeamColor()) {
                    queenMoves.add(new ChessMove(startPosition, position, null));
                }
                //else, I'm blocked.
                break;
            }
            if (chessboard.getPiece(position) == null) {
                //if the space is free, add it to the collection
                queenMoves.add(new ChessMove(startPosition, position, null));
            }
            //increment both row and col
            row++;
            col++;
        }
        //check right
        col = startPosition.getColumn() + 1;
        row = startPosition.getRow();
        while (col < 9) {
            position = new ChessPosition(row, col);
            if (chessboard.getPiece(position) != null) {
                if (chessboard.getPiece(position).getTeamColor() != chessboard.getPiece(startPosition).getTeamColor()) {
                    queenMoves.add(new ChessMove(startPosition, position, null));
                }
                break;
            }
            if (chessboard.getPiece(position) == null) {
                queenMoves.add(new ChessMove(startPosition, position, null));
            }
            col++;
        }
        //check down right
        col = startPosition.getColumn()+1;
        row = startPosition.getRow()-1;
        while (row > 0 && col <= 8) {
            //set new ChessPosition
            position = new ChessPosition(row, col);
            if (chessboard.getPiece(position) != null) {
                // and the piece is not on my team, add to array and break while loop
                if (chessboard.getPiece(position).getTeamColor() != chessboard.getPiece(startPosition).getTeamColor()) {
                    queenMoves.add(new ChessMove(startPosition, position, null));
                }
                //else, I'm blocked.
                break;
            }
            if (chessboard.getPiece(position) == null) {
                //if the space is free, add it to the collection
                queenMoves.add(new ChessMove(startPosition, position, null));
            }
            row--;
            col++;
        }
        //check down
        col = startPosition.getColumn();
        row = startPosition.getRow() - 1;
        while (row > 0) {
            position = new ChessPosition(row, col);
            if (chessboard.getPiece(position) != null) {
                if (chessboard.getPiece(position).getTeamColor() != chessboard.getPiece(startPosition).getTeamColor()) {
                    queenMoves.add(new ChessMove(startPosition, position, null));
                }
                break;
            }
            if (chessboard.getPiece(position) == null) {
                queenMoves.add(new ChessMove(startPosition, position, null));
            }
            row--;
        }
        //check down left
        col = startPosition.getColumn()-1;
        row = startPosition.getRow()-1;
        while (row > 0 && col > 0) {
            //set new ChessPosition
            position = new ChessPosition(row, col);
            if (chessboard.getPiece(position) != null) {
                // and the piece is not on my team, add to array and break while loop
                if (chessboard.getPiece(position).getTeamColor() != chessboard.getPiece(startPosition).getTeamColor()) {
                    queenMoves.add(new ChessMove(startPosition, position, null));
                }
                //else, I'm blocked.
                break;
            }
            if (chessboard.getPiece(position) == null) {
                //if the space is free, add it to the collection
                queenMoves.add(new ChessMove(startPosition, position, null));
            }
            row--;
            col--;
        }
        //check left
        col = startPosition.getColumn() - 1;
        row = startPosition.getRow();
        while (col > 0) {
            position = new ChessPosition(row, col);
            if (chessboard.getPiece(position) != null) {
                if (chessboard.getPiece(position).getTeamColor() != chessboard.getPiece(startPosition).getTeamColor()) {
                    queenMoves.add(new ChessMove(startPosition, position, null));
                }
                break;
            }
            if (chessboard.getPiece(position) == null) {
                queenMoves.add(new ChessMove(startPosition, position, null));
            }
            col--;
        }
        //check up left
        col = startPosition.getColumn()-1;
        row = startPosition.getRow()+1;
        while (row <= 8 && col > 0) {
            //set new ChessPosition
            position = new ChessPosition(row, col);
            if (chessboard.getPiece(position) != null) {
                // and the piece is not on my team, add to array and break while loop
                if (chessboard.getPiece(position).getTeamColor() != chessboard.getPiece(startPosition).getTeamColor()) {
                    queenMoves.add(new ChessMove(startPosition, position, null));
                }
                //else, I'm blocked.
                break;
            }
            if (chessboard.getPiece(position) == null) {
                //if the space is free, add it to the collection
                queenMoves.add(new ChessMove(startPosition, position, null));
            }
            row++;
            col--;
        }
        return queenMoves;
    }
}
