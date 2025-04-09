package ui;

import com.google.gson.*;
import websocket.messages.*;
import java.lang.reflect.Type;

public class ServerMessageDeserializer implements JsonDeserializer<ServerMessage> {

    @Override
    public ServerMessage deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject obj = json.getAsJsonObject();
        String type = obj.get("serverMessageType").getAsString(); // assuming this field exists

        return switch (type) {
            case "LOAD_GAME" -> context.deserialize(json, LoadGameMessage.class);
            case "ERROR" -> context.deserialize(json, ErrorMessage.class);
            case "NOTIFICATION" -> context.deserialize(json, NotificationMessage.class);
            default -> throw new JsonParseException("Unknown message type: " + type);
        };
    }
}
