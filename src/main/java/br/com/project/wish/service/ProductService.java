package br.com.project.wish.service;

import br.com.project.wish.dto.ProductDTO;
import br.com.project.wish.handler.IllegalArgumentException;
import br.com.project.wish.model.Product;

import java.util.List;
import java.util.Optional;

public interface ProductService {
  ProductDTO getProductById(Long productId);
  boolean productIsOnList(List<Product> productList, Long productId);
  boolean productListIsPresent(List<Product> productList, Long customerId);
}