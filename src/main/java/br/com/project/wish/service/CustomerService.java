package br.com.project.wish.service;

import br.com.project.wish.dto.CustomerDTO;

import java.util.List;

public interface CustomerService {
  CustomerDTO getCustomerById(Long customerId);
}