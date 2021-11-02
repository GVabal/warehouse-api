package dev.vabalas.warehouseapi.rest.shape;

import javax.persistence.EntityNotFoundException;

import static java.lang.String.format;

public class ShapeNotFoundException extends EntityNotFoundException {
  public ShapeNotFoundException(int id) {
    super(format("Shape with id %d not found", id));
  }
}
