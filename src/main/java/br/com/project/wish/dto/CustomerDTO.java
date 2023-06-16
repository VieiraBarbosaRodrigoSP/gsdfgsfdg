package br.com.project.wish.dto;

import br.com.project.wish.model.Customer;
import com.opengamma.strata.collect.ArgChecker;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;

public class CustomerDTO implements Serializable {
  private Long id;
  private String name;
  private String email;

  public CustomerDTO() { }

  public CustomerDTO(Long id, String name, String email) {
    this.id = id;
    this.name = name;
    this.email = email;
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

  public String getEmail() { return email; }

  public void setEmail(String email) {
    this.email = email;
  }

  public Customer toEntity() {
    Customer customer = new Customer();
    BeanUtils.copyProperties(this, customer);
    return customer;
  }

  public static CustomerDTO of(Customer customer) {
    ArgChecker.notNull(customer, "customer");
    CustomerDTO dto = new CustomerDTO();
    BeanUtils.copyProperties(customer, dto);
    return dto;
  }
}