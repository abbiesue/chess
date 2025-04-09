package server.websocket;

import com.google.gson.Gson;
import org.eclipse.jetty.websocket.api.Session;
import websocket.messages.ServerMessage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

public class ConnectionManager {
    public final ConcurrentHashMap<String, Connection> connections = new ConcurrentHashMap<>();

    public void add(String username, Integer gameID, Session session) {
        var connection = new Connection(username, gameID, session);
        connections.put(username, connection);
    }

    public void remove (String username) {
        connections.remove(username);
    }

    public void broadcast(String excludeUsername, Integer gameID, ServerMessage notification) throws IOException {
        var filteredList = new ArrayList<Connection>();
        for (var c : connections.values()) {
            if (c.session.isOpen()) {
                if (!c.username.equals(excludeUsername) && Objects.equals(c.gameID, gameID)) {
                    String json = new Gson().toJson(notification);
                    c.send(json);
                }
            } else {
                filteredList.add(c);
            }
        }
        // Clean up any connections that were left open.
        for (var c : filteredList) {
            connections.remove(c.username);
        }
    }
}
