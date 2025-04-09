package server.websocket;

import chess.ChessGame;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dataaccess.SQLAuthDAO;
import dataaccess.SQLGameDAO;
import dataaccess.SQLUserDAO;
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
                case MAKE_MOVE -> makeMove(session, username, (MakeMoveCommand) command);
                case LEAVE -> leaveGame(session, username, (LeaveGameCommand) command);
                case RESIGN -> resign(session, username, (ResignCommand) command);
            }
        } catch (ResponseException | IOException e) {
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

    private void resign(Session session, String username, ResignCommand command) {
        System.out.println("ResignCommand received");

    }

    private void leaveGame(Session session, String username, LeaveGameCommand command) {
        System.out.println("LeaveGameCommand received");
    }

    private void makeMove(Session session, String username, MakeMoveCommand command) {
        System.out.println("MakeMoveCommand received");
    }

    private void connect(Session session, String username, ConnectCommand command) throws ResponseException, IOException {
        System.out.println("ConnectCommand received"); //remove later
        ChessGame game = gameDAO.getGame(command.getGameID()).game();
        var message = new LoadGameMessage(game, command.getPlayerColor());
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
}
