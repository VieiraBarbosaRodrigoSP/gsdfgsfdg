package br.com.project.wish.service.impl;

import br.com.project.wish.dto.CustomerDTO;
import br.com.project.wish.handler.ResourceNotFoundException;
import br.com.project.wish.httpClient.CustomerClient;
import br.com.project.wish.service.CustomerService;
import feign.FeignException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static br.com.project.wish.constant.CommonConstant.logConstant.INFO_CUSTOMER_NOT_FOUND_BY_CUSTOMERID;
import static br.com.project.wish.constant.CommonConstant.logConstant.INFO_CUSTOMER_NOT_FOUND_BY_CUSTOMERID_LOG;

@Service
public class CustomerServiceImpl implements CustomerService {
  private static final Logger LOGGER = LoggerFactory.getLogger(CustomerServiceImpl.class);
  @Autowired
  private CustomerClient customerClient;
  @Override
  public CustomerDTO getCustomerById(Long customerId){
    try {
      return customerClient.getCustomerById(customerId);
    } catch (FeignException.FeignClientException e) {
      LOGGER.error(INFO_CUSTOMER_NOT_FOUND_BY_CUSTOMERID_LOG, customerId);
     throw new ResourceNotFoundException(INFO_CUSTOMER_NOT_FOUND_BY_CUSTOMERID + customerId);
    }
  }
}