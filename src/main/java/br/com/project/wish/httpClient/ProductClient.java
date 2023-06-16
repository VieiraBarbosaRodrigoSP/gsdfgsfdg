package br.com.project.wish.httpClient;

import br.com.project.wish.dto.ProductDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(value = "productClient", url = "${microservices.product}")
public interface ProductClient {
  @GetMapping(name = "ProductById", value = "/products/{Id}")
  ProductDTO getProductById(@PathVariable("Id") Long productId);
  @GetMapping(name = "AllProducts", value = "/products")
  List<ProductDTO> getAllProducts();
}