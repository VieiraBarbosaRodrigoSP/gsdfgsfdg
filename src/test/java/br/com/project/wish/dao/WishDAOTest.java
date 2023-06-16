package br.com.project.wish.dao;

import br.com.project.wish.BaseMocks;
import br.com.project.wish.handler.ResourceNotFoundException;
import com.mongodb.MongoException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static br.com.project.wish.constant.CommonConstant.baseMocksConstant.*;
import static br.com.project.wish.constant.CommonConstant.logConstant.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class WishDAOTest {
  @InjectMocks
  private WishDAO wishDAO;
  @Mock
  private MongoTemplate mongoTemplate;
  @Test
  void Should_findById_return_OK() {
    var customer = BaseMocks.getCustomer(1l, CUSTOMER_NAME_01, CUSTOMER_EMAIL_01);
    var productList =  BaseMocks. getProductList();
    var wish = BaseMocks.getWish(customer.getId(), customer, productList);
    when(mongoTemplate.findById(any(), any(Class.class))).thenReturn(wish);
    var actual = wishDAO.findById(1l);
    assertEquals(actual.get().getId(), 1l);
  }
  @Test
  void Should_findById_return_isEmpty() {
    when(mongoTemplate.findById(any(), any(Class.class))).thenReturn(null);
    var actual = wishDAO.findById(1l);
    assertEquals(actual, Optional.empty());
  }
  @Test
  void Should_findById_return_MongoException() {
    when(mongoTemplate.findById(any(), any(Class.class))).thenThrow(MongoException.class);
    var thrown = assertThrows( MongoException.class, () -> wishDAO.findById(1l) );
    assertTrue(thrown.getMessage().contains(ERROR_QUERY_WISH_BY_CUSTOMER_ID));
  }
  @Test
  void Should_findAndModify_retorn_Wish() {
    var customer = BaseMocks.getCustomer(1l, CUSTOMER_NAME_01, CUSTOMER_EMAIL_01);
    var productList =  BaseMocks. getProductList();
    var wish = BaseMocks.getWish(customer.getId(), customer, productList);
    when(mongoTemplate.findAndModify(any(), any(), any(), any(Class.class))).thenReturn(wish);
    var actual = wishDAO.findAndModify(wish);
    assertEquals(actual.getId(), wish.getCustomer().getId());
  }
  @Test
  void Should_findAndModify() {
    var customer = BaseMocks.getCustomer(1l, CUSTOMER_NAME_01, CUSTOMER_EMAIL_01);
    var productList =  BaseMocks. getProductList();
    var wish = BaseMocks.getWish(customer.getId(), customer, productList);
    when(mongoTemplate.findAndModify(any(), any(), any(), any(Class.class))).thenThrow(MongoException.class);
    var thrown = assertThrows( MongoException.class, () -> wishDAO.findAndModify(wish) );
    assertTrue(thrown.getMessage().contains(ERROR_UPDATE_PRODUCT_LIST_CUSTOMER_ID));
  }

  @Test
  void Should_findByCustomerAndProduct_return_isOK() {
    var customer = BaseMocks.getCustomer(3l, CUSTOMER_NAME_03, CUSTOMER_EMAIL_03);
    var productList =  BaseMocks. getProductList();
    var wish = BaseMocks.getWish(customer.getId(), customer, productList);
    when(mongoTemplate.findOne(any(), any(Class.class))).thenReturn(wish);
    var actual = wishDAO.findByCustomerAndProduct(1l, 1l);
    assertEquals(actual.getId(), 3l);
  }

  @Test
  void Should_findByCustomerAndProduct_return_NullPointerException() {
    when(mongoTemplate.findOne(any(), any(Class.class))).thenThrow(NullPointerException.class);
    var thrown = assertThrows( ResourceNotFoundException.class, () -> wishDAO.findByCustomerAndProduct(1l, 1l) );
    assertTrue(thrown.getMessage().contains(INFO_PRODUCT_NOT_FOUND_BY_CUSTOMERID));
  }

  @Test
  void Should_findByCustomerAndProduct_return_MongoException() {
    when(mongoTemplate.findOne(any(), any(Class.class))).thenThrow(MongoException.class);
    var thrown = assertThrows( MongoException.class, () -> wishDAO.findByCustomerAndProduct(1l, 1l) );
    assertTrue(thrown.getMessage().contains(ERROR_PRODUCT_NOT_FOUND_BY_CUSTOMERID));
  }
}