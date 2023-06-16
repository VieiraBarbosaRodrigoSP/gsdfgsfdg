package br.com.project.wish.controller;

import br.com.project.wish.dto.ProductDTO;
import br.com.project.wish.dto.ProductIdDTO;
import br.com.project.wish.dto.WishDTO;
import br.com.project.wish.service.WishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "/wishes")
public class WishController {
  @Autowired
  private WishService wishService;

  @GetMapping(value = "/{customerId}")
  @ResponseStatus(HttpStatus.OK)
  public WishDTO getAllWishesByCustomer(@PathVariable("customerId") Long customerId) {
    return wishService.getWishByCustomerId(customerId);
  }
  @GetMapping(value = "/{customerId}/products/{productId}")
  @ResponseStatus(HttpStatus.OK)
  public WishDTO getProductWishesByCustomerId(
      @PathVariable("customerId") Long customerId
      , @PathVariable("productId") Long productId) {
    return wishService.getWishByCustomerIdAndProductId(customerId, productId);
  }
  @PutMapping(value = "/{customerId}/products/add")
  @ResponseStatus(HttpStatus.ACCEPTED)
  public WishDTO addProductWishesByCustomerId(
      @PathVariable("customerId") Long customerId
      , @RequestBody @Valid ProductIdDTO product) {
    return wishService.addProductWishByCustomerId(customerId, product.getId());
  }
  @PutMapping(value = "/{customerId}/products/remove", consumes = APPLICATION_JSON_VALUE)
  @ResponseStatus(HttpStatus.ACCEPTED)
  public WishDTO removeProductWishesByCustomerId(
      @PathVariable("customerId") Long customerId
      , @RequestBody @Valid ProductIdDTO product) {
    return wishService.removeProductWishByCustomerId(customerId, product.getId());
  }
}