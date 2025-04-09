package websocket.commands;

import com.google.gson.*;
import java.lang.reflect.Type;

public class UserGameCommandDeserializer implements JsonDeserializer<UserGameCommand> {
    @Override
    public UserGameCommand deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObj = json.getAsJsonObject();
        String commandType = jsonObj.get("commandType").getAsString();

        return switch (UserGameCommand.CommandType.valueOf(commandType)) {
            case CONNECT -> context.deserialize(json, ConnectCommand.class);
            case MAKE_MOVE -> context.deserialize(json, MakeMoveCommand.class);
            case LEAVE -> context.deserialize(json, LeaveGameCommand.class);
            case RESIGN -> context.deserialize(json, ResignCommand.class);
        };
    }
}
