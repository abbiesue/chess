
package websocket;

import chess.ChessMove;
import com.google.gson.Gson;
import server.ResponseException;
import websocket.commands.*;
import websocket.messages.ErrorMessage;
import websocket.messages.ServerMessage;

import javax.websocket.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

@ClientEndpoint
public class WebSocketFacade extends Endpoint {
    ServerMessageObserver observer;
    Session session;

    public WebSocketFacade(String url, ServerMessageObserver observer) throws ResponseException {
        try {
            url = url.replace("http", "ws");
            URI socketURI = new URI(url + "/ws");
            this.observer = observer;

            WebSocketContainer container = ContainerProvider.getWebSocketContainer();
            //this is the problem child
            this.session = container.connectToServer(this, socketURI);

            //set message handler
            this.session.addMessageHandler((MessageHandler.Whole<String>) msg -> {
                try {
                    ServerMessage message = new Gson().fromJson(msg, ServerMessage.class);
                    observer.notify(message);
                } catch (Exception e) {
                    observer.notify(new ErrorMessage(e.getMessage()));
                }
            });
        } catch (DeploymentException | URISyntaxException | IOException e) {
            throw new ResponseException(500, e.getMessage());
        }
    }

    public void connect(String authToken, int gameID) throws ResponseException {
        var command = new ConnectCommand(authToken, gameID);
        sendCommand(command);
    }

    public void makeMove(String authToken, int gameID, ChessMove move) throws ResponseException {
        var command = new MakeMoveCommand(authToken, gameID, move);
        sendCommand(command);
    }

    public void leave(String authToken, int gameID) throws ResponseException {
        var command = new LeaveGameCommand(authToken, gameID);
        sendCommand(command);
    }

    public void resign (String authToken, int gameID) throws ResponseException {
        var command = new ResignCommand(authToken, gameID);
        sendCommand(command);
    }

    private void sendCommand(UserGameCommand command) throws ResponseException {
        try {
            this.session.getBasicRemote().sendText(new Gson().toJson(command));
        } catch (IOException e) {
            throw new ResponseException(500, e.getMessage());
        }
    }

    @Override
    public void onOpen(Session session, EndpointConfig endpointConfig) {
    }

//    private ChessGame getGame(int gameID) throws ResponseException {
//        var games = server.list(new ListRequest(authToken)).games();
//        for (int i = 0; i < games.size(); i++) {
//            if (games.get(i).gameID() == gameID) {
//                return games.get(i).game();
//            }
//        }
//        throw new ResponseException(400, "Game not Found");
//    }
}
