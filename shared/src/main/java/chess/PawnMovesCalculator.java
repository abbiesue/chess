package chess;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class PawnMovesCalculator implements PieceMovesCalculator {
    List<ChessMove> pawnMoves;

    @Override
    public Collection<ChessMove> pieceMoves(ChessBoard chessboard, ChessPosition position) {
        pawnMoves = new ArrayList<>();
        ChessPosition startPosition = position;
        int row = startPosition.getRow();
        int col = startPosition.getColumn();

        if (chessboard.getPiece(startPosition).getTeamColor() == ChessGame.TeamColor.BLACK) {
            if (row-1 > 0 && col-1 > 0) {
                position = new ChessPosition(row-1, col-1);
                // if there is a piece to the diag right (row-1, col-1) or diag left (row-1, col+1) && not on our team
                if (chessboard.getPiece(position) != null && chessboard.getPiece(position).getTeamColor() != ChessGame.TeamColor.BLACK) {
                    blackPawnMakeMove(position, startPosition);
                }
                if (col+1 < 9) {
                    position = new ChessPosition(row-1, col+1);
                    if (chessboard.getPiece(position) != null && chessboard.getPiece(position).getTeamColor() != ChessGame.TeamColor.BLACK) {
                        blackPawnMakeMove(position, startPosition);
                    }
                }

                row = startPosition.getRow();
                col = startPosition.getColumn();
                position = new ChessPosition(row-1, col);
                if (chessboard.getPiece(position) == null) { // if the space in front of the pawn is free (row-1):
                    blackPawnMakeMove(position, startPosition);
                    row = startPosition.getRow();
                    col = startPosition.getColumn();
                    if (startPosition.getRow() == 7) {
                        position = new ChessPosition(row-2,col);
                        if (chessboard.getPiece(position) == null) {
                            blackPawnMakeMove(position, startPosition);
                        }
                    }

                }
            }
        }

        if (chessboard.getPiece(startPosition).getTeamColor() == ChessGame.TeamColor.WHITE) {
            if (row+1 < 9 && col-1 > 0) {
                position = new ChessPosition(row+1, col-1);
                // if there is a piece to the diag right (row+1, col-1) or diag left (row+1, col+1) && not on our team
                if (chessboard.getPiece(position) != null && chessboard.getPiece(position).getTeamColor() != ChessGame.TeamColor.WHITE) {
                    whitePawnMakeMove(position, startPosition);
                }
                if (col+1 < 9) {
                    position = new ChessPosition(row+1, col+1);
                    if (chessboard.getPiece(position) != null && chessboard.getPiece(position).getTeamColor() != ChessGame.TeamColor.WHITE) {
                        whitePawnMakeMove(position, startPosition);
                    }
                }
            }
            row = startPosition.getRow();
            col = startPosition.getColumn();
            position = new ChessPosition(row+1, col);
            if (chessboard.getPiece(position) == null) { // if the space in front of the pawn is free (row+1):
                whitePawnMakeMove(position, startPosition);
                position = new ChessPosition(row+2,col);
                if (startPosition.getRow() == 2 && chessboard.getPiece(position) == null) {
                    whitePawnMakeMove(position, startPosition);
                }

            }
        }
        return pawnMoves;
    }

    @Override
    public boolean checkNAdd(int row, int col) {
        return true;
    } //not a lot of copied code so I'm just not gonna implement this method in the Pawn calculator.

    void promote(ChessPosition startPosition, ChessPosition endPosition) {
        pawnMoves.add(new ChessMove(startPosition, endPosition, ChessPiece.PieceType.QUEEN));
        pawnMoves.add(new ChessMove(startPosition, endPosition, ChessPiece.PieceType.BISHOP));
        pawnMoves.add(new ChessMove(startPosition, endPosition, ChessPiece.PieceType.KNIGHT));
        pawnMoves.add(new ChessMove(startPosition, endPosition, ChessPiece.PieceType.ROOK));
    }

    void blackPawnMakeMove(ChessPosition position, ChessPosition startPosition){
        if (position.getRow() == 1) {
            promote(startPosition, position);
        } else {
            pawnMoves.add(new ChessMove(startPosition, position, null)); //add
        }
    }

    void whitePawnMakeMove(ChessPosition position, ChessPosition startPosition){
        if (position.getRow() == 8) {
            promote(startPosition, position);
        } else {
            pawnMoves.add(new ChessMove(startPosition, position, null)); //add
        }
    }

}
