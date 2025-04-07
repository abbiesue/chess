package websocket.messages;

public class ErrorMessage extends ServerMessage {
    ServerMessageType type = ServerMessageType.ERROR;
    String errorMessage; //must contain "Error"

    public ErrorMessage(String errorMessage) {
        super(ServerMessageType.ERROR);
        this.errorMessage = errorMessage;
    }
}
