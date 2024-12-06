package tenkacheva.work.app.exceptions;

public class NotExistingException extends ServiceException {
    public NotExistingException(String message) {
        super(message);
    }
}
