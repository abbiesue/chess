package server.websocket;

import org.eclipse.jetty.websocket.api.Session;
import websocket.messages.ServerMessage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

public class ConnectionManager {
    public final ConcurrentHashMap<String, Connection> connections = new ConcurrentHashMap<>();

    public void add(String username, Session session) {
        var connection = new Connection(username, session);
        connections.put(username, connection);
    }

    public void remove (String username) {
        connections.remove(username);
    }

    public void broadcast(String excludeUsername, ServerMessage notification) throws IOException {
        var filteredList = new ArrayList<Connection>();
        for (var c : connections.values()) {
            if (c.session.isOpen()) {
                if (!c.username.equals(excludeUsername)) {
                    c.send(notification.toString());
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
