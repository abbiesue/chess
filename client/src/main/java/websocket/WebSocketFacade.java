package websocket;

import com.google.gson.Gson;
import server.ResponseException;
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






    @Override
    public void onOpen(Session session, EndpointConfig endpointConfig) {
    }
}
