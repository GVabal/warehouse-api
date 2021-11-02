package dev.vabalas.warehouseapi.rest.shape;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShapeService {
  private final ShapeRepository shapeRepository;

  public ShapeService(ShapeRepository shapeRepository) {
    this.shapeRepository = shapeRepository;
  }

  public List<Shape> getShapeList() {
    return shapeRepository.findAll();
  }

  public Shape getShape(int id) {
    return shapeRepository.findById(id)
                          .orElseThrow(() -> new ShapeNotFoundException(id));
  }

  public Shape addShape(Shape newShape) {
    return shapeRepository.save(newShape);
  }

  public Shape updateShape(Shape updatedShape) {
    if (shapeRepository.existsById(updatedShape.getId())) {
      return shapeRepository.save(updatedShape);
    }
    else {
      throw new ShapeNotFoundException(updatedShape.getId());
    }
  }

  public void removeShape(int id) {
    Shape shape = getShape(id);
    shapeRepository.delete(shape);
  }
}
