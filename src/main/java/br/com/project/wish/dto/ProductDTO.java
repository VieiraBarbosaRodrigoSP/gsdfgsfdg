package br.com.project.wish.dto;

import br.com.project.wish.model.Product;
import com.opengamma.strata.collect.ArgChecker;
import org.springframework.beans.BeanUtils;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import static br.com.project.wish.constant.CommonConstant.logConstant.*;


public class ProductDTO {
  @NotNull(message = INFO_CANNOT_BE_NULL)
  private Long id;
  @NotBlank(message = INFO_CANNOT_BE_NULL_OR_BLANK)
  private String name;
  @NotBlank(message = INFO_CANNOT_BE_NULL_OR_BLANK)
  @Size(min = 3, max = 250, message = INFO_MUST_BE_CHARACTERS)
  private String description;
  private double price;

  public ProductDTO() { }

  public ProductDTO(Long id, String name, String description, double price) {
    this.id = id;
    this.name = name;
    this.description = description;
    this.price = price;
  }

  public ProductDTO(Product product) {
    this.id = product.getId();
    this.name = product.getName();
    this.description = product.getDescription();
    this.price = product.getPrice();
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public double getPrice() {
    return price;
  }

  public void setPrice(double price) {
    this.price = price;
  }

  public Product toEntity() {
    Product product = new Product();
    BeanUtils.copyProperties(this, product);
    return product;
  }

  public static ProductDTO of(Product product) {
    ArgChecker.notNull(product, "product");
    ProductDTO dto = new ProductDTO();
    BeanUtils.copyProperties(product, dto);
    return dto;
  }
}
