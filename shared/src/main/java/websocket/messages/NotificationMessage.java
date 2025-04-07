package websocket.messages;

public class NotificationMessage extends ServerMessage{
    ServerMessageType type = ServerMessageType.NOTIFICATION;
    String message;

    public NotificationMessage(String message) {
        super(ServerMessageType.NOTIFICATION);
        this.message = message;
    }
}
