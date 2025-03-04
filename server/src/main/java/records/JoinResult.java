package records;

import chess.ChessGame;

public record JoinResult(ChessGame.TeamColor playerColor, int gameID) {}
