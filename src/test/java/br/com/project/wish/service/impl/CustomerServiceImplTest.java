package br.com.project.wish.service.impl;

import br.com.project.wish.BaseMocks;
import br.com.project.wish.constant.CommonConstant;
import br.com.project.wish.dto.CustomerDTO;
import br.com.project.wish.handler.ResourceNotFoundException;
import br.com.project.wish.httpClient.CustomerClient;
import feign.FeignException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static br.com.project.wish.constant.CommonConstant.baseMocksConstant.CUSTOMER_EMAIL_02;
import static br.com.project.wish.constant.CommonConstant.baseMocksConstant.CUSTOMER_NAME_02;
import static br.com.project.wish.constant.CommonConstant.logConstant.INFO_CUSTOMER_NOT_FOUND_BY_CUSTOMERID;
import static br.com.project.wish.constant.CommonConstant.logConstant.INFO_PRODUCT_NOT_FOUND_BY_PRODUCTID;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class CustomerServiceImplTest {
  @InjectMocks
  private CustomerServiceImpl customerServiceImpl;
  @Mock
  private CustomerClient customerClient;
  @Test
  void Should_getCustomerById_isOK() {
    CustomerDTO customer = BaseMocks.getCustomerDTO(1l, CUSTOMER_NAME_02, CUSTOMER_EMAIL_02);
    when(customerClient.getCustomerById(anyLong())).thenReturn(customer);
    var actual = customerServiceImpl.getCustomerById(1l);
    assertEquals(actual.getId(), 1l);
  }
  @Test
  void Should_getProductById_isResourceNotFoundException() {
    when(customerClient.getCustomerById(anyLong())).thenThrow(FeignException.FeignClientException.class);
    var thrown = assertThrows( ResourceNotFoundException.class, () -> customerServiceImpl.getCustomerById(1l) );
    assertTrue(thrown.getMessage().contains(INFO_CUSTOMER_NOT_FOUND_BY_CUSTOMERID));
  }
}