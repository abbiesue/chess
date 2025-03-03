package records;

import chess.ChessGame;

public record JoinRequest(String authtoken, ChessGame.TeamColor playerColor, String gameID) {
}
