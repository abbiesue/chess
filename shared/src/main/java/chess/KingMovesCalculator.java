package chess;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class KingMovesCalculator implements PieceMovesCalculator {
    @Override
    public Collection<ChessMove> pieceMoves(ChessBoard chessboard, ChessPosition position) {
        List<ChessMove> kingMoves = new ArrayList<>();
        ChessPosition startPosition = position;
        int row = startPosition.getRow();
        int col = startPosition.getColumn();
        //check (row+1, col)
        if (row+1 < 9) {
            position = new ChessPosition(row+1, col);
            if (chessboard.getPiece(position) != null) {
                if (chessboard.getPiece(position).getTeamColor() != chessboard.getPiece(startPosition).getTeamColor()) {
                    kingMoves.add(new ChessMove(startPosition, position, null));
                }

            } else {
                kingMoves.add(new ChessMove(startPosition, position, null));
            }
        }

        //check (row+1, col+1)
        if (row+1 < 9 && col+1 < 9) {
            position = new ChessPosition(row+1, col+1);
            if (chessboard.getPiece(position) != null) {
                if (chessboard.getPiece(position).getTeamColor() != chessboard.getPiece(startPosition).getTeamColor()) {
                    kingMoves.add(new ChessMove(startPosition, position, null));
                }

            } else {
                kingMoves.add(new ChessMove(startPosition, position, null));
            }
        }
        //check (row, col+1)
        if (col+1 < 9) {
            position = new ChessPosition(row, col+1);
            if (chessboard.getPiece(position) != null) {
                if (chessboard.getPiece(position).getTeamColor() != chessboard.getPiece(startPosition).getTeamColor()) {
                    kingMoves.add(new ChessMove(startPosition, position, null));
                }

            } else {
                kingMoves.add(new ChessMove(startPosition, position, null));
            }
        }
        //check (row-1, col+1)
        if (row-1 > 0 && col+1 < 9) {
            position = new ChessPosition(row-1, col+1);
            if (chessboard.getPiece(position) != null) {
                if (chessboard.getPiece(position).getTeamColor() != chessboard.getPiece(startPosition).getTeamColor()) {
                    kingMoves.add(new ChessMove(startPosition, position, null));
                }

            } else {
                kingMoves.add(new ChessMove(startPosition, position, null));
            }
        }
        //check (row-1, col)
        if (row-1 > 0) {
            position = new ChessPosition(row-1, col);
            if (chessboard.getPiece(position) != null) {
                if (chessboard.getPiece(position).getTeamColor() != chessboard.getPiece(startPosition).getTeamColor()) {
                    kingMoves.add(new ChessMove(startPosition, position, null));
                }

            } else {
                kingMoves.add(new ChessMove(startPosition, position, null));
            }
        }
        //check (row-1, col-1)
        if (row-1 > 0 && col-1 > 0) {
            position = new ChessPosition(row-1, col-1);
            if (chessboard.getPiece(position) != null) {
                if (chessboard.getPiece(position).getTeamColor() != chessboard.getPiece(startPosition).getTeamColor()) {
                    kingMoves.add(new ChessMove(startPosition, position, null));
                }

            } else {
                kingMoves.add(new ChessMove(startPosition, position, null));
            }
        }
        //check (row, col-1)
        if (col-1 > 0) {
            position = new ChessPosition(row, col-1);
            if (chessboard.getPiece(position) != null) {
                if (chessboard.getPiece(position).getTeamColor() != chessboard.getPiece(startPosition).getTeamColor()) {
                    kingMoves.add(new ChessMove(startPosition, position, null));
                }

            } else {
                kingMoves.add(new ChessMove(startPosition, position, null));
            }
        }
        //check (row+1, col-1)
        if (row+1 < 9 && col-1 > 0) {
            position = new ChessPosition(row+1, col-1);
            if (chessboard.getPiece(position) != null) {
                if (chessboard.getPiece(position).getTeamColor() != chessboard.getPiece(startPosition).getTeamColor()) {
                    kingMoves.add(new ChessMove(startPosition, position, null));
                }

            } else {
                kingMoves.add(new ChessMove(startPosition, position, null));
            }
        }

        return kingMoves;
    }
}
