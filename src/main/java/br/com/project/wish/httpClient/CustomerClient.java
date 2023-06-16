package br.com.project.wish.httpClient;

import br.com.project.wish.dto.CustomerDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient( name = "customerClient", url = "${microservices.customer}" + "/customers")
public interface CustomerClient {
  @GetMapping(value = "{Id}")
  CustomerDTO getCustomerById(@PathVariable("Id") Long customerId);
  @GetMapping
  List<CustomerDTO> getAllCustomers();
}