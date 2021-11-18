package dev.vabalas.warehouseapi.rest.shape;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static dev.vabalas.warehouseapi.rest.shape.ShapeValidator.ALLOWED_SHAPE_TYPES;

@Component
@ConditionalOnProperty(name = "shape.population.enable")
public class ShapePopulationComponent implements CommandLineRunner {
  private static final Logger LOG = LoggerFactory.getLogger(ShapePopulationComponent.class);

  private final List<String> shapeTypes = new ArrayList<>(ALLOWED_SHAPE_TYPES);

  private final int amountToPopulate;
  private final ShapeService shapeService;

  public ShapePopulationComponent(@Value("${shape.population.amount:1000}") int amountToPopulate, ShapeService shapeService) {
    this.amountToPopulate = amountToPopulate;
    this.shapeService = shapeService;
  }

  @Override
  public void run(String... args) {
    LOG.info("Populating shapes to database...");
    for (int i = 0; i < amountToPopulate; i++) {
      shapeService.addShape(new Shape(getRandomShapeType(), getRandomPrice()));
    }
    LOG.info("Populated {} shapes.", amountToPopulate);
  }

  private String getRandomShapeType() {
    return shapeTypes.get(new Random().nextInt(shapeTypes.size()));
  }

  private BigDecimal getRandomPrice() {
    BigDecimal result = BigDecimal.valueOf(Math.random()).multiply(BigDecimal.valueOf(100));
    result = result.setScale(2, RoundingMode.HALF_DOWN);
    return result;
  }
}
