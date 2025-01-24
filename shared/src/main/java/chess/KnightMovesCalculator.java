package chess;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class KnightMovesCalculator implements PieceMovesCalculator{

    @Override
    public Collection<ChessMove> pieceMoves(ChessBoard chessboard, ChessPosition position) {
        List<ChessMove> knightMoves = new ArrayList<>();
        ChessPosition startPosition = position;
        int row = startPosition.getRow();
        int col = startPosition.getColumn();

        //check up-right (row+2, col+1)
        if (row+2 < 9 && col+1 < 9) {
            position = new ChessPosition(row+2, col+1);
            if (chessboard.getPiece(position) != null) {
                if (chessboard.getPiece(position).getTeamColor() != chessboard.getPiece(startPosition).getTeamColor()) {
                    knightMoves.add(new ChessMove(startPosition, position,null));
                }
            } else {
                knightMoves.add(new ChessMove(startPosition, position,null));
            }
        }
        //check right-up (row+1, col+2)
        if (row+1 < 9 && col+2 < 9) {
            position = new ChessPosition(row+1, col+2);
            if (chessboard.getPiece(position) != null) {
                if (chessboard.getPiece(position).getTeamColor() != chessboard.getPiece(startPosition).getTeamColor()) {
                    knightMoves.add(new ChessMove(startPosition, position,null));
                }
            } else {
                knightMoves.add(new ChessMove(startPosition, position,null));
            }
        }
        //check right-down (row-1, col+2)
        if (row-1 > 0 && col+2 < 9) {
            position = new ChessPosition(row-1, col+2);
            if (chessboard.getPiece(position) != null) {
                if (chessboard.getPiece(position).getTeamColor() != chessboard.getPiece(startPosition).getTeamColor()) {
                    knightMoves.add(new ChessMove(startPosition, position,null));
                }
            } else {
                knightMoves.add(new ChessMove(startPosition, position,null));
            }
        }
        //check down-right (row-2, col+1)
        if (row-2 > 0 && col+1 < 9) {
            position = new ChessPosition(row-2, col+1);
            if (chessboard.getPiece(position) != null) {
                if (chessboard.getPiece(position).getTeamColor() != chessboard.getPiece(startPosition).getTeamColor()) {
                    knightMoves.add(new ChessMove(startPosition, position,null));
                }
            } else {
                knightMoves.add(new ChessMove(startPosition, position,null));
            }
        }
        //check down-left (row-2, col-1)
        if (row-2 > 0 && col-1 > 0) {
            position = new ChessPosition(row-2, col-1);
            if (chessboard.getPiece(position) != null) {
                if (chessboard.getPiece(position).getTeamColor() != chessboard.getPiece(startPosition).getTeamColor()) {
                    knightMoves.add(new ChessMove(startPosition, position,null));
                }
            } else {
                knightMoves.add(new ChessMove(startPosition, position,null));
            }
        }
        //check left-down (row-1, col-2)
        if (row-1 > 0 && col-2 > 0) {
            position = new ChessPosition(row-1, col-2);
            if (chessboard.getPiece(position) != null) {
                if (chessboard.getPiece(position).getTeamColor() != chessboard.getPiece(startPosition).getTeamColor()) {
                    knightMoves.add(new ChessMove(startPosition, position,null));
                }
            } else {
                knightMoves.add(new ChessMove(startPosition, position,null));
            }
        }
        //check left-up (row+1, col-2)
        if (row+1 < 9 && col-2 > 0) {
            position = new ChessPosition(row+1, col-2);
            if (chessboard.getPiece(position) != null) {
                if (chessboard.getPiece(position).getTeamColor() != chessboard.getPiece(startPosition).getTeamColor()) {
                    knightMoves.add(new ChessMove(startPosition, position,null));
                }
            } else {
                knightMoves.add(new ChessMove(startPosition, position,null));
            }
        }
        //check up-left (row+2, col-1)
        if (row+2 < 9 && col-1 > 0) {
            position = new ChessPosition(row+2, col-1);
            if (chessboard.getPiece(position) != null) {
                if (chessboard.getPiece(position).getTeamColor() != chessboard.getPiece(startPosition).getTeamColor()) {
                    knightMoves.add(new ChessMove(startPosition, position,null));
                }
            } else {
                knightMoves.add(new ChessMove(startPosition, position,null));
            }
        }
        return knightMoves;
    }
}
