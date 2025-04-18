package ui;

import chess.ChessGame;
import chess.ChessMove;
import chess.ChessPiece;
import chess.ChessPosition;
import records.JoinRequest;
import exceptions.ResponseException;
import server.ServerFacade;
import websocket.ServerMessageObserver;
import websocket.WebSocketFacade;

import java.util.Arrays;
import java.util.Objects;

import static chess.ChessGame.TeamColor.BLACK;

public class PlayerClient extends GameClient implements ServerMessageObserver{
    private WebSocketFacade ws;

    public PlayerClient(String authToken, ServerFacade server, String serverURL) throws ResponseException {
        this.authToken = authToken;
        this.server = server;
        ws = new WebSocketFacade(serverURL, this);
    }

    public String eval(String input) {
        try {
            var tokens = input.toLowerCase().split(" ");
            var cmd = (tokens.length > 0) ? tokens[0] : "help";
            var params = Arrays.copyOfRange(tokens, 1, tokens.length);
            return switch (cmd) {
                case "join" -> join(params);
                case "make" -> makeMove(params);
                case "highlight" -> highlightLegalMoves(params);
                case "redraw" -> redrawChessBoard(params);
                case "resign" -> "Are you sure? If so, type \"Yield\" to confirm";
                case "yield" -> resign();
                case "leave" -> leave();
                default -> help();
            };
        } catch (ResponseException ex) {
            return ex.getMessage();
        }
    }

    private String join(String... params) throws ResponseException {
        int listID = Integer.parseInt(params[0]);
        gameID = getIDFromList(listID);
        playerColor = params[1].toUpperCase();
        // only join if the game is not over
        server.join(new JoinRequest(authToken, playerColor, gameID));
        ChessGame.TeamColor connectColor = BLACK;
        if (Objects.equals(playerColor, "WHITE")) {
            connectColor = ChessGame.TeamColor.WHITE;
        }
        ws.connect(authToken, gameID, connectColor);
        return "\n joining game...";
    }

    public String makeMove(String... params) throws ResponseException{
        // validate parameters
        ChessPiece.PieceType promotionPiece = null;
        if (params.length < 1) {
            return "Did you mean \"make move <START_POSITION> <END_POSITION> <PROMOTION_PIECE(opt)>\"? Please try again.";
        } else if (params.length < 3 || params.length > 4) {
            throw new ResponseException(400, "Expected: make move <START_POSITION> <END_POSITION> <PROMOTION_PIECE(opt)>");
        } else if(params.length == 4) {
            if (isValidPromotionPiece(params[3])) {
                promotionPiece = stringToPieceType(params[3]);
            } else {
                throw new ResponseException(400, "That's an invalid promotion piece");
            }
        } else if (!Character.isAlphabetic(params[1].charAt(0)) || !Character.isDigit(params[1].charAt(1)) || params[1].length() > 2) {
            throw new ResponseException(400, "Expected: <START_POSITION> in form <letter#> ex: E2 or A8");
        } else if (!Character.isAlphabetic(params[2].charAt(0)) || !Character.isDigit(params[2].charAt(1)) || params[2].length() > 2) {
            throw new ResponseException(400, "Expected: <END_POSITION> in form <letter#> ex: E2 or A8");
        } else if (!validPosition(params[1]) || !validPosition(params[2])) {
            throw new ResponseException(400, "Position does not exist on the board");
        }
        ChessPosition startPosition = stringToPosition(params[1]);
        ChessPosition endPosition = stringToPosition(params[2]);
        ChessMove move = new ChessMove(startPosition,endPosition,promotionPiece);
        ws.makeMove(authToken, gameID, move);
        return "";
    }

    private ChessPiece.PieceType stringToPieceType(String param) throws ResponseException {
        param = param.toUpperCase();
        return switch (param) {
            case "ROOK" -> ChessPiece.PieceType.ROOK;
            case "KNIGHT" -> ChessPiece.PieceType.KNIGHT;
            case "BISHOP" -> ChessPiece.PieceType.BISHOP;
            case "KING" -> ChessPiece.PieceType.KING;
            case "QUEEN" -> ChessPiece.PieceType.QUEEN;
            case "PAWN" -> ChessPiece.PieceType.PAWN;
            default -> throw new ResponseException(400, "Invalid promotion piece type");
        };
    }

    private boolean isValidPromotionPiece(String param) {
        param = param.toUpperCase();
        return switch (param) {
            case "ROOK", "KNIGHT", "BISHOP", "KING", "QUEEN", "PAWN" -> true;
            default -> false;
        };
    }

    public String resign() throws ResponseException {
        ws.resign(authToken, gameID);
        return "You resigned. Better luck next time!";
    }

    public String leave() throws ResponseException {
        ws.leave(authToken, gameID);
        return "You've left gameplay.";
    }

    public String help() {
        return """
                make move <START_POSITION> <END_POSITION> <PROMOTION_PIECE(opt)> - to take your turn. promotion piece is for pawn promotion
                highlight legal moves <START_POSITION> - to highlight legal moves on the board for a specific piece
                redraw chess board - to redraw the chessboard
                resign - to forfeit and end the game
                leave - to exit the game window
                help - to list options
                """;
    }

    private boolean validPosition(String stringPos) {
        char rowChar = Character.toUpperCase(stringPos.charAt(0));
        int col = stringPos.charAt(1) - '0';
        if (rowChar != 'A' && rowChar != 'B' && rowChar != 'C' &&
                rowChar != 'D' && rowChar != 'E' && rowChar != 'F' &&
                rowChar != 'G' && rowChar != 'H') {
            return false;
        }
        if (col > 8 || col < 1) {
            return false;
        }
        return true;
    }
}
