package br.com.project.wish.controller;

import br.com.project.wish.BaseMocks;
import br.com.project.wish.handler.IllegalArgumentException;
import br.com.project.wish.handler.ResourceNotFoundException;
import br.com.project.wish.service.WishService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import javax.validation.Valid;

import static br.com.project.wish.constant.CommonConstant.baseMocksConstant.*;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Valid
@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = WishController.class)
class WishControllerTest {
  @Autowired
  protected MockMvc mockMvc;

  @MockBean
  WishService wishService;

  @BeforeEach
  public void setUp() {
    Mockito.reset(wishService);
  }

  @Test
  public void should_ReturnWishByCustomerId_isOk() throws Exception {
    var  customer = BaseMocks.getCustomerDTO(1l, CUSTOMER_NAME_01, CUSTOMER_EMAIL_01);
    var productList =  BaseMocks. getProductListDTO();
    var wish = BaseMocks.getWishDTO(customer.getId(), customer, productList);

    when(wishService.getWishByCustomerId(anyLong())).thenReturn(wish);

    mockMvc.perform(get("/wishes/1").accept(APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().contentType(APPLICATION_JSON))
        .andExpect(jsonPath("$.customer.name", is(CUSTOMER_NAME_01)))
        .andExpect(jsonPath("$.customer.email", is(CUSTOMER_EMAIL_01)))
        .andExpect(jsonPath("$.products[0].name", is(PRODUCT_NAME_01)))
        .andExpect(jsonPath("$.products[0].description", is(PRODUCT_DESCRIPTION_01)))
        .andExpect(jsonPath("$.products[0].price", is(PRODUCT_PRICE_01)));
  }

  @Test
  public void should_ReturnWishByCustomerId_isNotFound() throws Exception {
    when(wishService.getWishByCustomerId(anyLong())).thenThrow(ResourceNotFoundException.class);

    mockMvc.perform(get("/wishes/1").accept(APPLICATION_JSON))
        .andExpect(status().isNotFound());
  }

  @Test
  public void should_ReturnWishByCustomerIdAndProductId_isOK() throws Exception {
    var  customer = BaseMocks.getCustomerDTO(1l, CUSTOMER_NAME_01, CUSTOMER_EMAIL_01);
    var productList =  BaseMocks. getProductListDTO();
    var wish = BaseMocks.getWishDTO(customer.getId(), customer, productList);

    when(wishService.getWishByCustomerIdAndProductId(anyLong(), anyLong())).thenReturn(wish);

    mockMvc.perform(get("/wishes/1/products/5").accept(APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().contentType(APPLICATION_JSON))
        .andExpect(jsonPath("$.customer.name", is(CUSTOMER_NAME_01)))
        .andExpect(jsonPath("$.customer.email", is(CUSTOMER_EMAIL_01)))
        .andExpect(jsonPath("$.products[2].name", is(PRODUCT_NAME_03)))
        .andExpect(jsonPath("$.products[2].description", is(PRODUCT_DESCRIPTION_03)))
        .andExpect(jsonPath("$.products[2].price", is(PRODUCT_PRICE_03)));
  }

  @Test
  public void should_ReturnWishByCustomerIdAndProductId_isNotFound() throws Exception {
    when(wishService.getWishByCustomerIdAndProductId(anyLong(), anyLong())).thenThrow(ResourceNotFoundException.class);

    mockMvc.perform(get("/wishes/1/products/5").accept(APPLICATION_JSON))
        .andExpect(status().isNotFound());
  }

  @Test
  public void should_ReturnAddProductWishesByCustomerId_isOK() throws Exception {
    var payload = "{\"productId\":4}";

    mockMvc.perform(put("/wishes/4/products/add")
            .contentType(APPLICATION_JSON)
            .content(payload))
        .andExpect(status().isAccepted());
  }

  @Test
  public void should_ReturnAddProductWishesByCustomerId_isBadRequest() throws Exception {
    var payload = "{\"id\":4}";

    when(wishService.addProductWishByCustomerId(anyLong(), any())).thenThrow(IllegalArgumentException.class);

    mockMvc.perform(put("/wishes/4/products/add")
            .contentType(APPLICATION_JSON)
            .content(payload))
        .andExpect(status().isBadRequest());
  }

  @Test
  public void should_ReturnRemoveProductWishesByCustomerId_isAccepted() throws Exception {
    var payload = "{\"productId\":4}";

    mockMvc.perform(put("/wishes/4/products/remove")
            .contentType(APPLICATION_JSON)
            .content(payload))
        .andExpect(status().isAccepted());
  }

  @Test
  public void should_ReturnRemoveProductWishesByCustomerId_isBadRequest() throws Exception {
    var payload = "{\"id\":4}";

    when(wishService.addProductWishByCustomerId(anyLong(), any())).thenThrow(IllegalArgumentException.class);

    mockMvc.perform(put("/wishes/4/products/remove")
            .contentType(APPLICATION_JSON)
            .content(payload))
        .andExpect(status().isBadRequest());
  }
}