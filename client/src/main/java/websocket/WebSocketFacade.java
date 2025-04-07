package websocket;

import com.google.gson.Gson;
import server.ResponseException;
import websocket.messages.ErrorMessage;
import websocket.messages.ServerMessage;

import javax.websocket.Endpoint;
import javax.websocket.EndpointConfig;
import javax.websocket.Session;

public class WebSocketFacade extends Endpoint {
    ServerMessageObserver observer = new ServerMessageObserver();

    public void onMessage(String msg) {
        try {
            ServerMessage message = new Gson().fromJson(msg, ServerMessage.class);
            observer.notify(message);
        } catch (Exception e) {
            observer.notify(new ErrorMessage(e.getMessage()));
        }
    }


    @Override
    public void onOpen(Session session, EndpointConfig endpointConfig) {
    }
}
