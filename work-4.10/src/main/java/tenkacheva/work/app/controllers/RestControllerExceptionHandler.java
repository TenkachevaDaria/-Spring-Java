package tenkacheva.work.app.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.MissingRequestValueException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import tenkacheva.work.app.dtos.ErrorDTO;
import tenkacheva.work.app.exceptions.ControllerException;
import tenkacheva.work.app.exceptions.ExistingException;
import tenkacheva.work.app.exceptions.NotExistingException;
import tenkacheva.work.app.exceptions.ServiceException;
import tenkacheva.work.app.mappers.ErrorMapper;

@ControllerAdvice
public class RestControllerExceptionHandler {

    private final ErrorMapper errorMapper;

    @Autowired
    public RestControllerExceptionHandler(ErrorMapper errorMapper) {
        this.errorMapper = errorMapper;
    }

    @ExceptionHandler({ControllerException.class})
    public ResponseEntity<ErrorDTO> handleControllerException(ControllerException exception) {
        var error = errorMapper.controllerExceptionToErrorDTO(exception);
        return ResponseEntity
                .status(error.statusCode())
                .body(error);
    }

    @ExceptionHandler({NotExistingException.class})
    public ResponseEntity<ErrorDTO> handleServiceException(NotExistingException exception) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ErrorDTO(exception.getMessage(), HttpStatus.NOT_FOUND));
    }

    @ExceptionHandler({ExistingException.class})
    public ResponseEntity<ErrorDTO> handleServiceException(ExistingException exception) {
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(new ErrorDTO(exception.getMessage(), HttpStatus.CONFLICT));
    }

    @ExceptionHandler({ServiceException.class})
    public ResponseEntity<ErrorDTO> handleServiceException(ServiceException exception) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErrorDTO(exception.getMessage(), HttpStatus.BAD_REQUEST));
    }

    @ExceptionHandler({Exception.class})
    public ResponseEntity<ErrorDTO> handleException(Exception exception) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorDTO(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR));
    }

    @ExceptionHandler({MissingRequestHeaderException.class, MissingRequestValueException.class})
    public ResponseEntity<ErrorDTO> handleMissingRequestHeaderException(Exception exception) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErrorDTO(exception.getMessage(), HttpStatus.BAD_REQUEST));
    }
}
