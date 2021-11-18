package dev.vabalas.warehouseapi.rest.shape;

import dev.vabalas.warehouseapi.rest.Error;
import dev.vabalas.warehouseapi.rest.ValidationException;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static java.lang.String.format;

@Component
public class ShapeValidator {
  public static final Set<String> ALLOWED_SHAPE_TYPES = Set.of("ARROW", "CIRCLE", "HEART", "HEPTAGON",
                                                                "HEXAGON", "OCTAGON", "OVAL", "PARALLELOGRAM",
                                                                "PENTAGON", "RECTANGLE", "RHOMBUS", "SQUARE",
                                                                "STAR", "TRIANGLE");
  private static final int MAX_DECIMAL_PLACES = 2;

  public void validateShape(Shape shape) {
    List<Error> errors = new ArrayList<>();
    String type = shape.getType();
    if (type == null) {
      errors.add(new Error("Missing type"));
    }
    else {
      if (!ALLOWED_SHAPE_TYPES.contains(type)) {
        errors.add(new Error(format("Shape %s not allowed", type)));
      }
    }
    BigDecimal price = shape.getPrice();
    if (price == null) {
      errors.add(new Error("Missing price"));
    }
    else {
      if (shape.getPrice().signum() < 0) {
        errors.add(new Error("Price cannot be negative"));
      }
      if (shape.getPrice().scale() > MAX_DECIMAL_PLACES) {
        errors.add(new Error("Price cannot have more than 2 decimal places"));
      }
    }
    if (!errors.isEmpty()) {
      throw new ValidationException(errors);
    }
  }
}

