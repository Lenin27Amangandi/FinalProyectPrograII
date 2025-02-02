package BusinessLogic;

public class PinturaBLException extends RuntimeException{
    public PinturaBLException(String message) {
        super(message);
    }

    public PinturaBLException(String message, Throwable cause) {
        super(message, cause);
    }
}