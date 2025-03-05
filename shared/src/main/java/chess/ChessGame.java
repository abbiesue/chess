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
        board.resetBoard();
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
//  validMoves: Takes as input a position on the chessboard and returns all moves the
//    piece there can legally make. If there is no piece at that location, this method returns null.
//    A move is valid if it is a "piece move" for the piece at the input location and making that move
//    would not leave the team’s king in danger of check.

        List<ChessMove> validMoves = new ArrayList<>();
        if (board.getPiece(startPosition) != null || board.getPiece(startPosition).getTeamColor() == teamTurn) {
            //get pieceMoves at that position
            List<ChessMove> pieceMoves = (List<ChessMove>) board.getPiece(startPosition).pieceMoves(board, startPosition);
            // for each move on PieceMoves
            for (int i = 0; i < pieceMoves.size(); i++) {
                if (isValidMove(pieceMoves.get(i))) {
                    ChessMove moveClone = pieceMoves.get(i).clone();
                    validMoves.add(moveClone);
                }
            }
        }
        if (board.getPiece(startPosition) == null) {
            return null;
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
//  makeMove: Receives a given move and executes it, provided it is a legal move.
//    If the move is illegal, it throws an InvalidMoveException. A move is illegal if
//    it is not a "valid" move for the piece at the starting location, or if it’s not
//    the corresponding team's turn.
        ChessPosition startPosition = move.getStartPosition();
        if (board.getPiece(move.getStartPosition()) == null || board.getPiece(startPosition).getTeamColor() != teamTurn) {
            throw new InvalidMoveException("Invalid Move");
        }
        List<ChessMove> validMovesList = (List<ChessMove>) validMoves(startPosition);
        if (validMovesList.isEmpty()) {
            throw new InvalidMoveException("Invalid Move");
        }
        if (board.getPiece(move.getStartPosition()) == null || !validMovesList.contains(move)) {
            throw new InvalidMoveException("Invalid Move");
        }
        //make move
        TeamColor color = board.getPiece(move.getStartPosition()).getTeamColor();
        if (move.getPromotionPiece() == null) {
            ChessPiece newPiece = board.getPiece(move.getStartPosition()).clone();
            board.addPiece(move.getEndPosition(), newPiece);
            board.addPiece(move.getStartPosition(), null);
        } else {
            ChessPiece.PieceType type = move.getPromotionPiece();
            ChessPiece newPiece = new ChessPiece(color, type);
            board.addPiece(move.getEndPosition(), newPiece);
            board.addPiece(move.getStartPosition(), null);
        }
        if (color == TeamColor.BLACK) {
            teamTurn = TeamColor.WHITE;
        } else {
            teamTurn = TeamColor.BLACK;
        }
    }

    /**
     * Determines if the given team is in check
     *
     * @param teamColor which team to check for check
     * @return True if the specified team is in check
     */
    public boolean isInCheck(TeamColor teamColor) {
//  isInCheck: Returns true if the specified team’s King could be captured by an opposing piece.
        ChessPosition kingPosition = findMyKing(teamColor);
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                ChessPosition position = new ChessPosition(i,j);
                if (board.getPiece(position) != null && board.getPiece(position).getTeamColor() != teamColor) {
                    ChessPiece opponent = board.getPiece(position).clone();
                    List<ChessMove> pieceMoves = (List<ChessMove>) opponent.pieceMoves(board, position);
                    if (pieceMovesContainsKing(pieceMoves, kingPosition)) {return true;}
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
//  isInCheckmate: Returns true if the given team has no way to protect their king from being captured.
        if (!isInCheck(teamColor)) {
            return false;
        }
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (board.getPiece(new ChessPosition(i, j)) != null) {
                    ChessPiece pieceCheck = board.getPiece(new ChessPosition(i, j)).clone();
                    if (pieceCheck.getTeamColor() == teamColor && !validMoves(new ChessPosition(i, j)).isEmpty()) {
                        return false;
                    }
                }
            }
        }
        return true;

    }

    /**
     * Determines if the given team is in stalemate, which here is defined as having
     * no valid moves
     *
     * @param teamColor which team to check for stalemate
     * @return True if the specified team is in stalemate, otherwise false
     */
    public boolean isInStalemate(TeamColor teamColor) {
//  isInStalemate: Returns true if the given team has no legal moves but their king is not in immediate danger.
        // if the king is not in check
        if (isInCheck(teamColor)) {
            return false;
        }
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (board.getPiece(new ChessPosition(i,j)) != null) {
                    ChessPiece pieceCheck = board.getPiece(new ChessPosition(i,j)).clone();
                    if (pieceCheck.getTeamColor() == teamColor && !validMoves(new ChessPosition(i,j)).isEmpty()) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    /**
     * Sets this game's chessboard with a given board
     *
     * @param board the new board to use
     */
    public void setBoard(ChessBoard board) {
        this.board = board.clone();
    }

    /**
     * Gets the current chessboard
     *
     * @return the chessboard
     */
    public ChessBoard getBoard() {
        return board;
    }

    private ChessPosition findMyKing(TeamColor teamColor) {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                ChessPosition position = new ChessPosition(i,j);
                if (board.getPiece(position) != null) {
                    if (board.getPiece(position).getTeamColor() == teamColor && board.getPiece(position).getPieceType()
                            == ChessPiece.PieceType.KING) {
                        return position;
                    }
                }
            }
        }
        return null;
    }

    private boolean isValidMove(ChessMove move) {
        // used by ValidMoves to make a move and check the outcome. This only acts on a copy board
        // makes a move on a copy board and then checks if the king is in check of checkmate.
        // doesn't need to check if this is a valid move, because it should only be called by ValidMoves

        //make copy board
        ChessBoard boardCopy = board.clone();
        TeamColor color = boardCopy.getPiece(move.getStartPosition()).getTeamColor();;
        //make move
        if (move.getPromotionPiece() == null) {
            ChessPiece newPiece = board.getPiece(move.getStartPosition()).clone();
            board.addPiece(move.getEndPosition(), newPiece);
            board.addPiece(move.getStartPosition(), null);
        } else {
            ChessPiece.PieceType type = move.getPromotionPiece();
            ChessPiece newPiece = new ChessPiece(color, type);
            board.addPiece(move.getEndPosition(), newPiece);
            board.addPiece(move.getStartPosition(), null);
        }

        if (isInCheck(color)) {
            board = boardCopy;
            return false;
        } else {
            board = boardCopy;
            return true;
        }
    }

    private boolean pieceMovesContainsKing(List<ChessMove> pieceMoves, ChessPosition kingPosition) {
        for (ChessMove move : pieceMoves) {
            if (move.getEndPosition().equals(kingPosition)) {
                return true;
            }
        }
        return false;
    }
}
