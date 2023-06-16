package br.com.project.wish.service.impl;

import br.com.project.wish.BaseMocks;
import br.com.project.wish.constant.CommonConstant;
import br.com.project.wish.dto.ProductDTO;
import br.com.project.wish.handler.IllegalArgumentException;
import br.com.project.wish.handler.ResourceNotFoundException;
import br.com.project.wish.httpClient.ProductClient;
import br.com.project.wish.model.Product;
import feign.FeignException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static br.com.project.wish.constant.CommonConstant.baseMocksConstant.*;
import static br.com.project.wish.constant.CommonConstant.logConstant.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class ProductServiceImplTest {
  @InjectMocks
  private ProductServiceImpl productServiceImpl;
  @Mock
  private ProductClient productClient;
  @Test
  void Should_getProductById_isOK() {
    var product = BaseMocks.getProductDTO(1l, PRODUCT_NAME_01, PRODUCT_DESCRIPTION_01, PRODUCT_PRICE_01);
    when(productClient.getProductById(anyLong())).thenReturn(product);
    var actual = productServiceImpl.getProductById(1l);
    assertEquals(actual.getId(), 1l);
  }

  @Test
  void Should_getProductById_isResourceNotFoundException() {
    when(productClient.getProductById(anyLong())).thenThrow(FeignException.FeignClientException.class);
    var thrown = assertThrows( ResourceNotFoundException.class, () -> productServiceImpl.getProductById(1l) );
    assertTrue(thrown.getMessage().contains(INFO_PRODUCT_NOT_FOUND_BY_PRODUCTID));
  }

  @Test
  void Should_productIsOnList_TRUE() {
    var productList = BaseMocks.getProductList();
    var actual = productServiceImpl.productIsOnList(productList, 1l);
    assertTrue(actual);
  }

  @Test
  void Should_productIsOnList_FALSE() {
    var productList = BaseMocks.getProductList();
    var actual = productServiceImpl.productIsOnList(productList, 40l);
    assertFalse(actual);
  }

  @Test
  void Should_productListIsPresent_TRUE() {
    var productList = BaseMocks.getProductList();
    var actual = productServiceImpl.productListIsPresent(productList, 1l);
    assertTrue(actual);
  }

  @Test
  void Should_productListIsPresent_() {
    var thrown = assertThrows( IllegalArgumentException.class, () -> productServiceImpl.productListIsPresent(List.of(), 40l) );
    assertTrue(thrown.getMessage().contains(INFO_PRODUCT_LIST_IS_EMPTY_BY_CUSTOMER_ID));
  }
}