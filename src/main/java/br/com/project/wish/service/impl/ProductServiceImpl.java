package br.com.project.wish.service.impl;

import br.com.project.wish.dto.ProductDTO;
import br.com.project.wish.handler.IllegalArgumentException;
import br.com.project.wish.handler.ResourceNotFoundException;
import br.com.project.wish.httpClient.ProductClient;
import br.com.project.wish.model.Product;
import br.com.project.wish.service.ProductService;
import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static br.com.project.wish.constant.CommonConstant.logConstant.INFO_PRODUCT_LIST_IS_EMPTY_BY_CUSTOMER_ID;
import static br.com.project.wish.constant.CommonConstant.logConstant.INFO_PRODUCT_NOT_FOUND_BY_PRODUCTID;

@Service
public class ProductServiceImpl implements ProductService {
  @Autowired
  private ProductClient productClient;
  @Override
  public ProductDTO getProductById(Long productId) {
    try {
      return productClient.getProductById(productId);
    } catch (FeignException.FeignClientException e) {
      throw new ResourceNotFoundException(INFO_PRODUCT_NOT_FOUND_BY_PRODUCTID + productId);
    }
  }
  @Override
  public boolean productIsOnList(List<Product> productList, Long productId){
    return productList.stream().anyMatch(productMap -> productMap.getId().equals(productId));
  }
  @Override
  public boolean productListIsPresent(List<Product> productList, Long customerId) {
    if(!productList.isEmpty()) return true;
    throw new IllegalArgumentException(INFO_PRODUCT_LIST_IS_EMPTY_BY_CUSTOMER_ID + customerId);
  }
}