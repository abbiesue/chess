package server.websocket;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import websocket.messages.*;

import java.lang.reflect.Type;

public class ServerMessageSerializer implements JsonSerializer<ServerMessage> {

    @Override
    public JsonElement serialize(ServerMessage src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject obj = new JsonObject();

        // Add the common serverMessageType field
        obj.addProperty("serverMessageType", src.getServerMessageType().name());

        // Add subclass-specific fields
        switch (src.getServerMessageType()) {
            case LOAD_GAME -> {
                LoadGameMessage msg = (LoadGameMessage) src;
                obj.add("game", context.serialize(msg.getGame()));
            }
            case NOTIFICATION -> {
                NotificationMessage msg = (NotificationMessage) src;
                obj.addProperty("message", msg.getMessage());
            }
            case ERROR -> {
                ErrorMessage msg = (ErrorMessage) src;
                obj.addProperty("message", msg.getMessage());
            }
        }

        return obj;
    }
}

