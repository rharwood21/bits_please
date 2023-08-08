package multiplayer;

public class MultiplayerException extends RuntimeException {
    public MultiplayerException(String message) {
        super(message);
    }

    public MultiplayerException(String message, Throwable cause) {
        super(message, cause);
    }
}