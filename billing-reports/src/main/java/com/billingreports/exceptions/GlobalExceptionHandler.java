package com.billingreports.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import java.time.LocalDate;
import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UsernameOrEmailAlreadyExistsException.class)
    public ResponseEntity<ErrorDetails> userNameAlreadyExistException(UsernameOrEmailAlreadyExistsException exception, WebRequest webRequest) {
        LocalDateTime localDate = LocalDateTime.now();
        ErrorDetails errorDetails = new ErrorDetails(localDate, exception.getMessage(), webRequest.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(UsernameOrPasswordIncorrectException.class)
    public ResponseEntity<ErrorDetails> usernameOrPasswordIncorrectException(UsernameOrPasswordIncorrectException exception, WebRequest webRequest) {
        LocalDateTime localDate = LocalDateTime.now();
        ErrorDetails errorDetails = new ErrorDetails(localDate, exception.getMessage(), webRequest.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorDetails> userNotFoundException(UserNotFoundException exception, WebRequest webRequest) {
        LocalDateTime localDate = LocalDateTime.now();
        ErrorDetails errorDetails = new ErrorDetails(localDate, exception.getMessage(), webRequest.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ValidDateRangeException.class)
    public ResponseEntity<ErrorDetails> validDateRangeException(ValidDateRangeException exception, WebRequest webRequest) {
        LocalDateTime localDate = LocalDateTime.now();
        ErrorDetails errorDetails = new ErrorDetails(localDate, exception.getMessage(), webRequest.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidUserException.class)
    public ResponseEntity<ErrorDetails> invalidUserException(InvalidUserException exception, WebRequest webRequest) {
        LocalDateTime localDate = LocalDateTime.now();
        ErrorDetails errorDetails = new ErrorDetails(localDate, exception.getMessage(), webRequest.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.UNAUTHORIZED);
    }
}
