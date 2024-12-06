package tenkacheva.work.app.exceptions;

import org.springframework.http.HttpStatus;

public class ControllerException extends RuntimeException {
    private final HttpStatus statusCode;

    public ControllerException(String message, HttpStatus statusCode) {
        super(message);
        this.statusCode = statusCode;
    }

    public HttpStatus getStatusCode() {
        return statusCode;
    }
}
