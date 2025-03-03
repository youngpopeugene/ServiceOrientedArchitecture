package humanbeing.ejb.exception;

public class UnprocessableEntityException extends RuntimeException{
    public UnprocessableEntityException(String message) {
        super(message);
    }
    public UnprocessableEntityException() {
        super();
    }
}
