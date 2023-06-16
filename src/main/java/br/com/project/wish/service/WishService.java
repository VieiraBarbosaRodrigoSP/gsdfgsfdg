package br.com.project.wish.service;

import br.com.project.wish.dto.ProductDTO;
import br.com.project.wish.dto.WishDTO;

public interface WishService {
  WishDTO getWishByCustomerId(Long customerId);
  WishDTO getWishByCustomerIdAndProductId(Long customerId, Long productId);
  WishDTO addProductWishByCustomerId(Long customerId, Long productId);
  WishDTO removeProductWishByCustomerId(Long customerId, Long productId);
}