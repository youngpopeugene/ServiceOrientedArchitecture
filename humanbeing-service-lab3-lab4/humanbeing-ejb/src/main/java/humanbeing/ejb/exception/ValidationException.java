package humanbeing.ejb.exception;

public class ValidationException extends RuntimeException {
    public ValidationException(String message) {
        super(message);
    }
    public ValidationException() {
        super();
    }
}
