package chess;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class PawnMovesCalculator implements PieceMovesCalculator {

    @Override
    public Collection<ChessMove> pieceMoves(ChessBoard chessboard, ChessPosition position) {
        List<ChessMove> pawnMoves = new ArrayList<>();
        ChessPosition startPosition = position;
        int row = startPosition.getRow();
        int col = startPosition.getColumn();

        if (chessboard.getPiece(startPosition).getTeamColor() == ChessGame.TeamColor.BLACK) {
            if (row-1 > 0 && col-1 > 0) {
                position = new ChessPosition(row-1, col-1);
                // if there is a piece to the diag right (row-1, col-1) or diag left (row-1, col+1) && not on our team
                if (chessboard.getPiece(position) != null && chessboard.getPiece(position).getTeamColor() != ChessGame.TeamColor.BLACK) {
                    if (position.getRow() == 1) {
                        promote(startPosition, position, pawnMoves);
                    } else {
                        pawnMoves.add(new ChessMove(startPosition, position, null)); //add
                    }
                }
                if (col+1 < 9) {
                    position = new ChessPosition(row-1, col+1);
                    if (chessboard.getPiece(position) != null && chessboard.getPiece(position).getTeamColor() != ChessGame.TeamColor.BLACK) {
                        if (position.getRow() == 1) {
                            promote(startPosition, position, pawnMoves);
                        } else {
                            pawnMoves.add(new ChessMove(startPosition, position, null)); //add
                        }
                    }
                }
            }
            row = startPosition.getRow();
            col = startPosition.getColumn();
            if (row-1 > 0) {
                position = new ChessPosition(row-1, col);
                if (chessboard.getPiece(position) == null) { // if the space in front of the pawn is free (row-1):
                    if (position.getRow() == 1) {
                        promote(startPosition, position, pawnMoves);
                    } else {
                        pawnMoves.add(new ChessMove(startPosition, position, null)); //add
                    }
                    row = startPosition.getRow();
                    col = startPosition.getColumn();
                    if (startPosition.getRow() == 7) {
                        position = new ChessPosition(row-2,col);
                        if (chessboard.getPiece(position) == null) {
                            if (position.getRow() == 1) {
                                promote(startPosition, position, pawnMoves);
                            } else {
                                pawnMoves.add(new ChessMove(startPosition, position, null)); //add
                            }
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
                    if (position.getRow() == 8) {
                        promote(startPosition, position, pawnMoves);
                    } else {
                        pawnMoves.add(new ChessMove(startPosition, position, null)); //add
                    }
                }
                if (col+1 < 9) {
                    position = new ChessPosition(row+1, col+1);
                    if (chessboard.getPiece(position) != null && chessboard.getPiece(position).getTeamColor() != ChessGame.TeamColor.WHITE) {
                        if (position.getRow() == 8) {
                            promote(startPosition, position, pawnMoves);
                        } else {
                            pawnMoves.add(new ChessMove(startPosition, position, null)); //add
                        }
                    }
                }
            }
            row = startPosition.getRow();
            col = startPosition.getColumn();
            if (row+1 < 9) {
                position = new ChessPosition(row+1, col);
                if (chessboard.getPiece(position) == null) { // if the space in front of the pawn is free (row+1):
                    if (position.getRow() == 8) {
                        promote(startPosition, position, pawnMoves);
                    } else {
                        pawnMoves.add(new ChessMove(startPosition, position, null)); //add
                    }
                    row = startPosition.getRow();
                    col = startPosition.getColumn();
                    if (startPosition.getRow() == 2) {
                        position = new ChessPosition(row+2,col);
                        if (chessboard.getPiece(position) == null) {
                            if (position.getRow() == 8) {
                                promote(startPosition, position, pawnMoves);
                            } else {
                                pawnMoves.add(new ChessMove(startPosition, position, null)); //add
                            }
                        }
                    }

                }
            }
        }
        return pawnMoves;
    }

    @Override
    public boolean checkNAdd(int row, int col) {
        return true;
    } //not a lot of copied code so I'm just not gonna implement this method in the Pawn calculator.

    void promote(ChessPosition startPosition, ChessPosition endPosition, List<ChessMove> pawnMoves) {
        pawnMoves.add(new ChessMove(startPosition, endPosition, ChessPiece.PieceType.QUEEN));
        pawnMoves.add(new ChessMove(startPosition, endPosition, ChessPiece.PieceType.BISHOP));
        pawnMoves.add(new ChessMove(startPosition, endPosition, ChessPiece.PieceType.KNIGHT));
        pawnMoves.add(new ChessMove(startPosition, endPosition, ChessPiece.PieceType.ROOK));
    }
}
