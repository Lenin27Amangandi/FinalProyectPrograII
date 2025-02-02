package BusinessLogic;


public class UsuarioBLException extends RuntimeException {

    public UsuarioBLException(String message) {
        super(message);
    }

    public UsuarioBLException(String message, Throwable cause) {
        super(message, cause);
    }
}
