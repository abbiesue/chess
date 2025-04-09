package server.websocket;

import chess.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dataaccess.SQLAuthDAO;
import dataaccess.SQLGameDAO;
import dataaccess.SQLUserDAO;
import model.GameData;
import org.eclipse.jetty.websocket.api.RemoteEndpoint;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import server.ResponseException;
import websocket.commands.*;
import websocket.messages.ErrorMessage;
import websocket.messages.LoadGameMessage;
import websocket.messages.NotificationMessage;
import websocket.messages.ServerMessage;

import java.io.IOException;
import java.util.Objects;

@WebSocket
public class WebSocketHandler {
    static final ResponseException UNAUTHORIZED = new ResponseException(401, "Error: unauthorized");
    static final ResponseException BAD_REQUEST = new ResponseException(400, "Error: bad request");

    private final ConnectionManager connections = new ConnectionManager();

    public WebSocketHandler(SQLAuthDAO authDAO, SQLGameDAO gameDAO, SQLUserDAO userDAO) {
        this.authDAO = authDAO;
        this.gameDAO = gameDAO;
        this.userDAO = userDAO;
    }

    SQLAuthDAO authDAO;
    SQLGameDAO gameDAO;
    SQLUserDAO userDAO;

    @OnWebSocketMessage
    public void onMessage(Session session, String msg) {
        try {
            Gson gson = new GsonBuilder()
                    .registerTypeAdapter(UserGameCommand.class, new UserGameCommandDeserializer())
                    .create();

            UserGameCommand command = gson.fromJson(msg, UserGameCommand.class);
            //validate authToken
            String authToken = command.getAuthToken();
            if (authDAO.getAuth(authToken) == null) {
                throw UNAUTHORIZED;
            }
            //validate gameID
            int gameID = command.getGameID();
            if (gameDAO.getGame(gameID) == null) {
                throw BAD_REQUEST;
            }

            //store username
            String username = authDAO.getAuth(authToken).username();

            // save session to connection manager map
            saveSession( username, command.getGameID(), session);

            switch (command.getCommandType()) {
                case CONNECT -> connect(session, username, (ConnectCommand) command);
                case MAKE_MOVE -> makeMove(username, (MakeMoveCommand) command);
                case LEAVE -> leaveGame(username, (LeaveGameCommand) command);
                case RESIGN -> resign(session, username, (ResignCommand) command);
            }
        } catch (ResponseException | IOException | InvalidMoveException e) {
            sendMessage(session.getRemote(), new ErrorMessage("Error: " + e.getMessage()));
        }
    }



    private void sendMessage(RemoteEndpoint remote, ServerMessage message) {
        try {
            String json = new Gson().toJson(message);
            remote.sendString(json);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void saveSession(String username, Integer gameID, Session session) {
        connections.add(username, gameID, session);
    }

    private void resign(Session session, String username, ResignCommand command) throws ResponseException, IOException {
        System.out.println("ResignCommand received");
        if (Objects.equals(username, gameDAO.getGame(command.getGameID()).blackUsername()) ||
                Objects.equals(username, gameDAO.getGame(command.getGameID()).whiteUsername())) {
            if (gameDAO.isGameOver(command.getGameID())) {
                throw new ResponseException(400, "Error: game is already over");
            }
            //update game to over
            gameDAO.setGameOver(command.getGameID());
            //notifies everyone that the user resigned
            connections.broadcast(username, command.getGameID(), new NotificationMessage(buildResignMessage(username)));
            sendMessage(session.getRemote(), new NotificationMessage("You resigned."));
        } else {
            throw UNAUTHORIZED;
        }
    }

    private void leaveGame(String username, LeaveGameCommand command) throws ResponseException, IOException {
        System.out.println("LeaveGameCommand received");
        //if the username given is on the gameData, remove it
        GameData gameData = gameDAO.getGame(command.getGameID());
        if (Objects.equals(gameData.blackUsername(), username)){
            gameDAO.updateGame(command.getGameID(), ChessGame.TeamColor.BLACK, null);
        } else if (Objects.equals(gameData.whiteUsername(), username)) {
            gameDAO.updateGame(command.getGameID(), ChessGame.TeamColor.WHITE, null);
        }
        //remove session from connections
        connections.remove(username);
        //broadcast that this person has left the game
        connections.broadcast(username, command.getGameID(),new NotificationMessage(buildLeaveNotification(username)));

    }

    private void makeMove(String username, MakeMoveCommand command) throws ResponseException, InvalidMoveException, IOException {
        System.out.println("MakeMoveCommand received");
        //check that the username matches the username of one of the players
        if (!Objects.equals(username, gameDAO.getGame(command.getGameID()).blackUsername()) &&
                !Objects.equals(username, gameDAO.getGame(command.getGameID()).whiteUsername())) {
            throw UNAUTHORIZED;
        }
        //check that game is not over
        if (gameDAO.isGameOver(command.getGameID())) {
            throw new ResponseException(400, "Error: game is over");
        }
        //store variables
        ChessMove move = command.getMove();
        ChessGame game = gameDAO.getGame(command.getGameID()).game();
        ChessGame.TeamColor playerColor;
        ChessGame.TeamColor opponentColor;
        if (Objects.equals(gameDAO.getGame(command.getGameID()).blackUsername(), username)) {
            playerColor = ChessGame.TeamColor.BLACK;
            opponentColor = ChessGame.TeamColor.WHITE;
        } else {playerColor = ChessGame.TeamColor.WHITE; opponentColor = ChessGame.TeamColor.BLACK;}
        //check it is the player's turn
        if (game.getTeamTurn() != playerColor) {
            throw new ResponseException(400, "Error: It's not your turn");
        }
        //check that move is a valid move, update game
        game.makeMove(move);
        //update gameDAO
        gameDAO.updateAfterMove(command.getGameID(), game);
        //send loadgame to everyone including the client
        connections.broadcast(null, command.getGameID(), new LoadGameMessage(game));
        //send notification that move was made to all other clients
        connections.broadcast(username, command.getGameID(), new NotificationMessage(buildMadeMoveMessage(username,move,game)));
        //if the move results in a check, checkmate, or stalemate notify all clients
        notifyOpponentsStatus(opponentColor, game, command.getGameID());

    }

    private void connect(Session session, String username, ConnectCommand command) throws ResponseException, IOException {
        System.out.println("ConnectCommand received"); //remove later
        ChessGame game = gameDAO.getGame(command.getGameID()).game();
        var message = new LoadGameMessage(game);
        sendMessage(session.getRemote(), message);
        //build notification
        String notification = buildConnectNotification(username, command);
        //broadcasts to everyone else someone joined.
        connections.broadcast(username, command.getGameID(), new NotificationMessage(notification));
    }

    private String buildConnectNotification(String username, ConnectCommand command) {
        String notification = username;
        if (command.getPlayerColor() == null) {
            notification = notification.concat(" joined as an observer");
        } else {
            notification = notification.concat(" joined as the " + command.getPlayerColor() + " player");
        }
        return notification;
    }

    private String buildLeaveNotification(String username) {
        return username + " has left the game";
    }

    private String buildResignMessage(String username) {
        return username + " admits defeat... They resign...";
    }

    private String buildMadeMoveMessage(String username, ChessMove move, ChessGame game) {
        String notification = username + " moved their ";
        ChessBoard board = game.getBoard();
        ChessPiece piece = board.getPiece(move.getEndPosition());
        return notification.concat(piece + " to " + positionToString(move.getEndPosition()));
    }

    private String positionToString(ChessPosition position) {
        int row = position.getRow();
        int col = position.getColumn();
        String positionString = "";
        switch (row) {
            case 1 -> positionString = "A";
            case 2 -> positionString = "B";
            case 3 -> positionString = "C";
            case 4 -> positionString = "D";
            case 5 -> positionString = "E";
            case 6 -> positionString = "F";
            case 7 -> positionString = "G";
            case 8 -> positionString = "H";
        }
        return positionString.concat(String.valueOf(col));
    }

    private void notifyOpponentsStatus(ChessGame.TeamColor opponentColor, ChessGame game, int gameID) throws IOException {
        if (game.isInCheckmate(opponentColor)) {
            connections.broadcast(null, gameID, new NotificationMessage(opponentColor + " is in checkmate!"));
        } else if (game.isInCheck(opponentColor)) {
            connections.broadcast(null, gameID, new NotificationMessage(opponentColor + " is in check!"));
        }
        if (game.isInStalemate(opponentColor)) {
            connections.broadcast(null, gameID, new NotificationMessage(opponentColor + " is in a stalemate"));
        }
    }


}
