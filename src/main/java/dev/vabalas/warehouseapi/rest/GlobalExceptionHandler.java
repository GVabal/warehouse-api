package dev.vabalas.warehouseapi.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.List;

@ControllerAdvice
public class GlobalExceptionHandler {
  private static final Logger LOG = LoggerFactory.getLogger(GlobalExceptionHandler.class);

  @ExceptionHandler(ValidationException.class)
  public ResponseEntity<ErrorResponse> handleValidationException(ValidationException e) {
    return generateResponseEntity(HttpStatus.BAD_REQUEST, e.getErrors());
  }

  @ExceptionHandler(EntityNotFoundException.class)
  public ResponseEntity<ErrorResponse> handleEntityNotFoundException(EntityNotFoundException e) {
    return generateResponseEntity(HttpStatus.NOT_FOUND, List.of(new Error(e.getMessage())));
  }

  @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
  public ResponseEntity<ErrorResponse> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
    return generateResponseEntity(HttpStatus.METHOD_NOT_ALLOWED, List.of(new Error(e.getMessage())));
  }

  @ExceptionHandler(HttpMessageNotReadableException.class)
  public ResponseEntity<ErrorResponse> handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
    return generateResponseEntity(HttpStatus.NOT_ACCEPTABLE, List.of(new Error(e.getMessage())));
  }

  @ExceptionHandler(RuntimeException.class)
  public ResponseEntity<ErrorResponse> handleInternalServerErrorException(RuntimeException e) {
    LOG.warn("Internal server error occurred: {}", e.getMessage());
    return generateResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR, List.of(new Error(e.getMessage())));
  }

  private ResponseEntity<ErrorResponse> generateResponseEntity(HttpStatus status, List<Error> errors) {
    return new ResponseEntity<>(new ErrorResponse(status.value(), status.getReasonPhrase(), LocalDateTime.now().toString(),
                                                  errors.stream().map(Error::message).toList()), status);
  }

  private record ErrorResponse(int code, String error, String timestamp, List<String> errorMessages) {}
}
