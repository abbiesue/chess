package chess;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * For a class that can manage a chess game, making moves on a board
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessGame {
    private TeamColor teamTurn;
    ChessBoard board;

    public ChessGame() {
        teamTurn = TeamColor.WHITE;
        board = new ChessBoard();
    }

    /**
     * @return Which team's turn it is
     */
    public TeamColor getTeamTurn() {
        return teamTurn;
    }

    /**
     * Set's which teams turn it is
     *
     * @param team the team whose turn it is
     */
    public void setTeamTurn(TeamColor team) {
        teamTurn = team;
    }

    /**
     * Enum identifying the 2 possible teams in a chess game
     */
    public enum TeamColor {
        WHITE,
        BLACK
    }

    /**
     * Gets a valid moves for a piece at the given location
     *
     * @param startPosition the piece to get valid moves for
     * @return Set of valid moves for requested piece, or null if no piece at
     * startPosition
     */
    public Collection<ChessMove> validMoves(ChessPosition startPosition) {
        List<ChessMove> validMoves = new ArrayList<>();
        ChessBoard boardCopy = board.clone(); //make this a deep copy

        if (board.getPiece(startPosition) == null){
            return validMoves;
        } else {
            List<ChessMove> pieceMoves = (List<ChessMove>) board.getPiece(startPosition).pieceMoves(board, startPosition);
            //get pieceMoves at that position
            // for each move on PieceMoves
            for (int i = 0; i < pieceMoves.size(); i++) {

            }
                // create a copy of the board
                // make move
                // if king is not in check after the move is made
                    // add the valid moves collection.
            //return valid moves collection.
        }
        return validMoves;
    }

    /**
     * Makes a move in a chess game
     *
     * @param move chess move to preform
     * @throws InvalidMoveException if move is invalid
     */
    public void makeMove(ChessMove move) throws InvalidMoveException {
        throw new RuntimeException("Not implemented");
    }

    /**
     * Determines if the given team is in check
     *
     * @param teamColor which team to check for check
     * @return True if the specified team is in check
     */
    public boolean isInCheck(TeamColor teamColor) {
        // my team is in check if my king is in danger of being taken. so, if I run piecemoves on all the pieces on the board
        // that don't share my color, and my king's position is on any of those lists, then my king is in check
        //find my king
        ChessPosition kingPosition = findMyKing(teamColor);

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                ChessPosition position = new ChessPosition(i,j);
                if (board.getPiece(position) != null) {
                    if (board.getPiece(position).getTeamColor() != teamColor) {
                        System.out.println("Opponent found! At: " + position.toString());
                        ChessPiece opponent = board.getPiece(position).clone();
                        List<ChessMove> pieceMoves = (List<ChessMove>) opponent.pieceMoves(board, position);
                        for (ChessMove move : pieceMoves) {
                            System.out.println("Checking position: " + move.getEndPosition().toString());
                            if (move.getEndPosition().equals(kingPosition)) {
                                return true;
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    /**
     * Determines if the given team is in checkmate
     *
     * @param teamColor which team to check for checkmate
     * @return True if the specified team is in checkmate
     */
    public boolean isInCheckmate(TeamColor teamColor) {
        throw new RuntimeException("Not implemented");
    }

    /**
     * Determines if the given team is in stalemate, which here is defined as having
     * no valid moves
     *
     * @param teamColor which team to check for stalemate
     * @return True if the specified team is in stalemate, otherwise false
     */
    public boolean isInStalemate(TeamColor teamColor) {
        throw new RuntimeException("Not implemented");
    }

    /**
     * Sets this game's chessboard with a given board
     *
     * @param board the new board to use
     */
    public void setBoard(ChessBoard board) {
        System.out.println("Before: ");
        System.out.print(board.toString());
        System.out.print(this.board.toString());
        this.board = board.clone();
        System.out.println("After: ");
        System.out.print(this.board.toString());
    }

    /**
     * Gets the current chessboard
     *
     * @return the chessboard
     */
    public ChessBoard getBoard() {
        throw new RuntimeException("Not implemented");
    }

    public ChessPosition findMyKing(TeamColor teamColor) {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                ChessPosition position = new ChessPosition(i,j);
                if (board.getPiece(position) != null) {
                    if (board.getPiece(position).getTeamColor() == teamColor && board.getPiece(position).getPieceType() == ChessPiece.PieceType.KING) {
                        System.out.println("King found! At:" + position.toString());
                        return position;
                    }
                }
            }
        }
        return null;
    }
}
