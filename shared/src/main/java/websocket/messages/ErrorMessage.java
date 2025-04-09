package websocket.messages;

public class ErrorMessage extends ServerMessage {
    String errorMessage; //must contain "Error"

    public ErrorMessage(String errorMessage) {
        super(ServerMessageType.ERROR);
        this.errorMessage = errorMessage;
    }

    public String getMessage() {
        return errorMessage;
    }
}
