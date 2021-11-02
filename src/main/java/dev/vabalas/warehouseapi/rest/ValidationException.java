package dev.vabalas.warehouseapi.rest;

import java.util.List;

public class ValidationException extends RuntimeException {
  private final List<Error> errors;

  public ValidationException(List<Error> errors) {
    super();
    this.errors = errors;
  }

  public List<Error> getErrors() {
    return errors;
  }

  @Override
  public String toString() {
    return "ValidationException{" +
           "errors=" + errors +
           '}';
  }
}
