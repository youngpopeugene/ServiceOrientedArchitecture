package humanbeing.ejb.exception;

public class PageException extends RuntimeException {
    public PageException(String message) {
        super(message);
    }
    public PageException() {
        super();
    }
}
