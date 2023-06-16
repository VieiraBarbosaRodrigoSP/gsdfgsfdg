package br.com.project.wish.dto;

import br.com.project.wish.model.Wish;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.opengamma.strata.collect.ArgChecker;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

@JsonIgnoreProperties
public class WishDTO implements Serializable {
  private Long id;
  private CustomerDTO customer;
  private List<ProductDTO> products;

  public WishDTO(Long id, CustomerDTO customer, List<ProductDTO> products) {
    this.id = id;
    this.customer = customer;
    this.products = products;
  }

  public WishDTO() { }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public CustomerDTO getCustomer() {
    return customer;
  }

  public void setCustomer(CustomerDTO customer) {
    this.customer = customer;
  }

  public List<ProductDTO> getProducts() {
    return products;
  }

  public void setProducts(List<ProductDTO> products) {
    this.products = products;
  }

  public static WishDTO of(Wish wish) {
    ArgChecker.notNull(wish, "wish");
    var customer = CustomerDTO.of(wish.getCustomer());
    WishDTO dto = new WishDTO();
    dto.setCustomer(customer);
    var products = wish.getProducts().stream().map(product -> ProductDTO.of(product)).collect(Collectors.toList());
    dto.setProducts(products);
    BeanUtils.copyProperties(wish, dto);
    return dto;
  }
}