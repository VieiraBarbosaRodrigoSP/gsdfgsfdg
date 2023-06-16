package br.com.project.wish.service.impl;

import br.com.project.wish.BaseMocks;
import br.com.project.wish.dao.WishDAO;
import br.com.project.wish.handler.IllegalArgumentException;
import br.com.project.wish.model.Wish;
import br.com.project.wish.service.CustomerService;
import br.com.project.wish.service.ProductService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static br.com.project.wish.constant.CommonConstant.baseMocksConstant.*;
import static br.com.project.wish.constant.CommonConstant.logConstant.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class WishServiceImplTest {
  @InjectMocks
  private WishServiceImpl wishServiceImpl;
  @Mock
  private WishDAO wishDAO;
  @Mock
  private CustomerService customerService;
  @Mock
  private ProductService productService;
  @Test
  void Should_getWishByCustomerId_wishIsPresent() {
    var customer = BaseMocks.getCustomer(1l, CUSTOMER_NAME_01, CUSTOMER_EMAIL_01);
    var productList =  BaseMocks. getProductList();
    var wish = BaseMocks.getWish(customer.getId(), customer, productList);

    when(wishDAO.findById(anyLong())).thenReturn(Optional.of(wish));

    var actual = wishServiceImpl.getWishByCustomerId(1l);
    assertEquals(actual.getCustomer().getName(), wish.getCustomer().getName());
  }
  @Test
  void Should_getWishByCustomerId_wishIsEmpty() {
    var customer = BaseMocks.getCustomerDTO(2l, CUSTOMER_NAME_02, CUSTOMER_EMAIL_02);

    when(wishDAO.findById(anyLong())).thenReturn(Optional.of(new Wish()));
    when(customerService.getCustomerById(anyLong())).thenReturn(customer);

    var actual = wishServiceImpl.getWishByCustomerId(2l);

    assertEquals(actual.getCustomer().getName(), CUSTOMER_NAME_02);
  }

  @Test
  void Should_addProductWishByCustomerId_productIsOnList_isNotInTheList() {
    var product = BaseMocks.getProductDTO(3l, PRODUCT_NAME_03, PRODUCT_DESCRIPTION_03, PRODUCT_PRICE_03);

    var customer = BaseMocks.getCustomer(3l, CUSTOMER_NAME_03, CUSTOMER_EMAIL_03);
    var productList =  BaseMocks. getProductList();
    var wish = BaseMocks.getWish(customer.getId(), customer, productList);

    when(wishDAO.findById(anyLong())).thenReturn(Optional.of(wish));
    when(productService.getProductById(anyLong())).thenReturn(product);
    when(productService.productIsOnList(anyList(), any())).thenReturn(true);

    var thrown = assertThrows( IllegalArgumentException.class, () -> wishServiceImpl.addProductWishByCustomerId(3l, product.getId()) );
    assertTrue(thrown.getMessage().contains(INFO_PRODUCT_ALREADY_EXIST_IN_PRODUCT_LIST));
  }

  @Test
  void Should_addProductWishByCustomerId_productIsOnList_isInTheList() {
    var product = BaseMocks.getProductDTO(3l, PRODUCT_NAME_03, PRODUCT_DESCRIPTION_03, PRODUCT_PRICE_03);

    var customer = BaseMocks.getCustomer(3l, CUSTOMER_NAME_03, CUSTOMER_EMAIL_03);
    var productList =  BaseMocks. getProductList();
    var wish = BaseMocks.getWish(customer.getId(), customer, productList);

    var updatedWish = BaseMocks.getWish(customer.getId(), customer, productList);
    updatedWish.getProducts().add(product.toEntity());

    when(wishDAO.findById(anyLong())).thenReturn(Optional.of(wish));
    when(productService.getProductById(anyLong())).thenReturn(product);
    when(productService.productIsOnList(anyList(), any())).thenReturn(false);
    when(wishDAO.findAndModify(any())).thenReturn(updatedWish);


    var actual = wishServiceImpl.addProductWishByCustomerId(3l, product.getId());

    assertEquals(actual.getProducts().size(), updatedWish.getProducts().size());
  }

  @Test
  void Should_addProductWishByCustomerId_ListIsEmpty() {
    var product = BaseMocks.getProductDTO(3l, PRODUCT_NAME_03, PRODUCT_DESCRIPTION_03, PRODUCT_PRICE_03);
    var customer = BaseMocks.getCustomer(3l, CUSTOMER_NAME_03, CUSTOMER_EMAIL_03);
    var wish = BaseMocks.getWish(customer.getId(), customer, List.of());
    var updatedWish = BaseMocks.getWish(customer.getId(), customer, List.of(product.toEntity()));

    when(wishDAO.findById(anyLong())).thenReturn(Optional.of(wish));
    when(productService.getProductById(anyLong())).thenReturn(product);
    when(productService.productIsOnList(anyList(), any())).thenReturn(false);
    when(wishDAO.findAndModify(any())).thenReturn(updatedWish);

    var actual = wishServiceImpl.addProductWishByCustomerId(3l, product.getId());

    assertEquals(actual.getProducts().size(), updatedWish.getProducts().size());
  }

  @Test
  void Should_addProductWishByCustomerId_listSizeLarger_notInsert() {
    var product = BaseMocks.getProductDTO(3l, PRODUCT_NAME_03, PRODUCT_DESCRIPTION_03, PRODUCT_PRICE_03);
    var customer = BaseMocks.getCustomer(3l, CUSTOMER_NAME_03, CUSTOMER_EMAIL_03);
    var productList =  BaseMocks. getProductList();
    var wish = BaseMocks.getWish(customer.getId(), customer, productList);
    wish.getProducts().addAll(productList);
    wish.getProducts().addAll(productList);
    wish.getProducts().addAll(productList);

    when(wishDAO.findById(anyLong())).thenReturn(Optional.of(wish));
    when(productService.productIsOnList(anyList(), any())).thenReturn(false);

    var thrown = assertThrows( IllegalArgumentException.class, () -> wishServiceImpl.addProductWishByCustomerId(3l, product.getId()) );
    assertTrue(thrown.getMessage().contains(INFO_CANNOT_ADD_ITEM_TO_LIST));
  }

  @Test
  void Should_removeProductWishByCustomerId_productListIsPresent_isTrue() {
    var customer = BaseMocks.getCustomer(3l, CUSTOMER_NAME_03, CUSTOMER_EMAIL_03);
    var productList =  BaseMocks. getProductList();
    var wish = BaseMocks.getWish(customer.getId(), customer, productList);

    var updatedWish = BaseMocks.getWish(customer.getId(), customer, productList);
    updatedWish.getProducts().remove(3);

    when(wishDAO.findById(anyLong())).thenReturn(Optional.of(wish));
    when(productService.productListIsPresent(anyList(), anyLong())).thenReturn(true);
    when(productService.productIsOnList(anyList(), anyLong())).thenReturn(true);
    when(wishDAO.findAndModify(any())).thenReturn(updatedWish);

    var actual = wishServiceImpl.removeProductWishByCustomerId(1l, 3l);
    assertEquals(actual.getProducts().size(), updatedWish.getProducts().size());
  }

  @Test
  void Should_NOT_removeProductWishByCustomerId_productListIsPresent_isFalse() {
    var customer = BaseMocks.getCustomer(3l, CUSTOMER_NAME_03, CUSTOMER_EMAIL_03);
    var productList =  BaseMocks. getProductList();
    var wish = BaseMocks.getWish(customer.getId(), customer, productList);

    var updatedWish = BaseMocks.getWish(customer.getId(), customer, productList);
    updatedWish.getProducts().remove(3);

    when(wishDAO.findById(anyLong())).thenReturn(Optional.of(wish));
    when(productService.productListIsPresent(anyList(), anyLong())).thenReturn(false);
    when(productService.productIsOnList(anyList(), anyLong())).thenReturn(true);
    when(wishDAO.findAndModify(any())).thenReturn(updatedWish);

    wishServiceImpl.removeProductWishByCustomerId(1l, 3l);
    verify(wishDAO, times(0)).findAndModify(any());
  }

  @Test
  void Should_NOT_removeProductWishByCustomerId_productIsOnList_isFalse() {
    var customer = BaseMocks.getCustomer(3l, CUSTOMER_NAME_03, CUSTOMER_EMAIL_03);
    var productList =  BaseMocks. getProductList();
    var wish = BaseMocks.getWish(customer.getId(), customer, productList);

    var updatedWish = BaseMocks.getWish(customer.getId(), customer, productList);
    updatedWish.getProducts().remove(3);

    when(wishDAO.findById(anyLong())).thenReturn(Optional.of(wish));
    when(productService.productListIsPresent(anyList(), anyLong())).thenReturn(true);
    when(productService.productIsOnList(anyList(), anyLong())).thenReturn(false);
    when(wishDAO.findAndModify(any())).thenReturn(updatedWish);

    var thrown = assertThrows( IllegalArgumentException.class, () -> wishServiceImpl.removeProductWishByCustomerId(1l, 3l) );
    assertTrue(thrown.getMessage().contains(INFO_PRODUCT_NOT_ON_LIST));
  }

  @Test
  void getWishByCustomerIdAndProductId() {
    var customer = BaseMocks.getCustomer(3l, CUSTOMER_NAME_03, CUSTOMER_EMAIL_03);
    var productList =  BaseMocks. getProductList();
    var wish = BaseMocks.getWish(customer.getId(), customer, productList);

    when(wishDAO.findByCustomerAndProduct(anyLong(), anyLong())).thenReturn(wish);

    var actual = wishServiceImpl.getWishByCustomerIdAndProductId(3l, 3l);
    assertEquals(actual.getProducts().size(), 1);
  }
}