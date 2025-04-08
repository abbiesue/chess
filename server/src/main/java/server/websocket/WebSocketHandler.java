package server.websocket;

import com.google.gson.Gson;
import dataaccess.SQLAuthDAO;
import org.eclipse.jetty.websocket.api.RemoteEndpoint;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import server.ResponseException;
import websocket.commands.*;
import websocket.messages.ErrorMessage;

@WebSocket
public class WebSocketHandler {
    static final ResponseException UNAUTHORIZED = new ResponseException(401, "Error: unauthorized");

    private final ConnectionManager connections = new ConnectionManager();

    public WebSocketHandler(SQLAuthDAO authDAO) {
        this.authDAO = authDAO;
    }

    SQLAuthDAO authDAO;

    @OnWebSocketMessage
    public void onMessage(Session session, String msg) {
        try {
            UserGameCommand command = new Gson().fromJson(msg, UserGameCommand.class);
            //validate authToken
            String authToken = command.getAuthToken();
            if (authDAO.getAuth(authToken) == null) {
                throw UNAUTHORIZED;
            }
            //store username
            String username = authDAO.getAuth(authToken).username();

            // save session to connection manager map
            saveSession(command.getGameID(), session);

            switch (command.getCommandType()) {
                case CONNECT -> connect(session, username, (ConnectCommand) command);
                case MAKE_MOVE -> makeMove(session, username, (MakeMoveCommand) command);
                case LEAVE -> leaveGame(session, username, (LeaveGameCommand) command);
                case RESIGN -> resign(session, username, (ResignCommand) command);
            }
        } catch (ResponseException e) {
            sendMessage(session.getRemote(), new ErrorMessage("Error: " + e.getMessage()));
        }
    }

    private void sendMessage(RemoteEndpoint remote, ErrorMessage errorMessage) {
    }

    private void saveSession(Integer gameID, Session session) {
    }

    private void resign(Session session, String username, ResignCommand command) {
        System.out.println("ResignCommand received");
        //notifies all observers and players.
        //
    }

    private void leaveGame(Session session, String username, LeaveGameCommand command) {
        System.out.println("LeaveGameCommand received");
    }

    private void makeMove(Session session, String username, MakeMoveCommand command) {
        System.out.println("MakeMoveCommand received");
    }

    private void connect(Session session, String username, ConnectCommand command) {
        //
        System.out.println("ConnectCommand received");
    }

}
