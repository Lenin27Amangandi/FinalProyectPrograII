package Framework;

public class PinturaException extends RuntimeException{
    public PinturaException(String message) {
        super(message);
    }

    public PinturaException(String message, Throwable cause) {
        super(message, cause);
    }
}