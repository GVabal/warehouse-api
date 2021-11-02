package dev.vabalas.warehouseapi.rest.shape;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("shapes")
public class ShapeController {
  private final ShapeService shapeService;
  private final ShapeValidator shapeValidator;

  public ShapeController(ShapeService shapeService, ShapeValidator shapeValidator) {
    this.shapeService = shapeService;
    this.shapeValidator = shapeValidator;
  }

  @GetMapping
  public List<Shape> getShapes() {
    return shapeService.getShapeList();
  }

  @GetMapping("{id}")
  public Shape getShape(@PathVariable int id) {
    return shapeService.getShape(id);
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public Shape addShape(@RequestBody Shape.Dto dto) {
    Shape newShape = dto.toShape();
    shapeValidator.validateShape(newShape);
    return shapeService.addShape(newShape);
  }

  @PutMapping("{id}")
  public Shape updateShape(@PathVariable int id, @RequestBody Shape.Dto dto) {
    Shape updatedShape = dto.toShape();
    shapeValidator.validateShape(updatedShape);
    updatedShape.setId(id);
    return shapeService.updateShape(updatedShape);
  }

  @DeleteMapping("{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void removeShape(@PathVariable int id) {
    shapeService.removeShape(id);
  }
}

