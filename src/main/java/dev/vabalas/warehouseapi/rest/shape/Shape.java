package dev.vabalas.warehouseapi.rest.shape;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.math.BigDecimal;

@Entity
public class Shape {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;
  private String type;
  private BigDecimal price;

  protected Shape() {}

  protected Shape(String type, BigDecimal price) {
    this.type = type;
    this.price = price;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getType() {
    return type;
  }

  public BigDecimal getPrice() {
    return price;
  }

  @Override
  public String toString() {
    return "Shape{" +
           "id=" + id +
           ", type=" + type +
           ", price=" + price +
           '}';
  }

  public record Dto(String type, BigDecimal price) {
    public Shape toShape() {
      return new Shape(type, price);
    }
  }
}
