package br.com.project.wish.service.impl;

import br.com.project.wish.dao.WishDAO;
import br.com.project.wish.dto.ProductDTO;
import br.com.project.wish.dto.WishDTO;
import br.com.project.wish.handler.IllegalArgumentException;
import br.com.project.wish.helper.StringHelper;
import br.com.project.wish.model.Product;
import br.com.project.wish.model.Wish;
import br.com.project.wish.service.CustomerService;
import br.com.project.wish.service.ProductService;
import br.com.project.wish.service.WishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static br.com.project.wish.constant.CommonConstant.logConstant.*;

@Service
public class WishServiceImpl implements WishService {
  @Autowired
  private WishDAO wishDAO;
  @Autowired
  private CustomerService customerService;
  @Autowired
  private ProductService productService;

  @Override
  public WishDTO getWishByCustomerId(Long customerId) {
    var wish = this.findWishByCustomer(customerId);
    return WishDTO.of(wish.get());
  }
  @Override
  public WishDTO addProductWishByCustomerId(Long customerId, Long productId) {
    var wish = this.findWishByCustomer(customerId);
    var product = productService.getProductById(productId);
    this.setProductList(wish, product);
    var updatedWish = wishDAO.findAndModify(wish.get());
    return WishDTO.of(updatedWish);
  }
  private Optional<Wish> findWishByCustomer(Long customerId){
    var wish = wishDAO.findById(customerId);
    this.setCustomer(wish, customerId);
    return wish;
  }
  private void setCustomer(Optional<Wish> wish, Long customerId){
    if(wish.isEmpty() || wish.get().getCustomer()==null) {
      var customer = customerService.getCustomerById(customerId);
      wish.get().setId(customer.getId());
      wish.get().setCustomer(customer.toEntity());
      wish.get().setProducts(List.of(new Product()));
    }
  }
  private boolean validatesSizeProductList(List<Product> productList) {
    var productListSize = productList.size();
    if (productListSize <= 20) return true;
    var payload = StringHelper.convertObjectTo(productList);
    throw new IllegalArgumentException(INFO_CANNOT_ADD_ITEM_TO_LIST + PAYLOAD + payload);
  }
  private void setProductList(Optional<Wish> wish, ProductDTO product){
    if (!wish.get().getProducts().isEmpty()) {
      this.addProductToList(wish, product);
    } else {
      wish.get().setProducts(List.of(product.toEntity()));
    }
  }
  private Optional<Wish> addProductToList(Optional<Wish> wish, ProductDTO product){
    if(this.validatesSizeProductList(wish.get().getProducts()) &&
        !productService.productIsOnList(wish.get().getProducts(), product.getId())) {
        wish.get().getProducts().add(product.toEntity());
        return wish;
    }
    var payload = StringHelper.convertObjectTo(product);
    throw new IllegalArgumentException(INFO_PRODUCT_ALREADY_EXIST_IN_PRODUCT_LIST + wish.get().getCustomer().getId() + PRODUCT_ID + product.getId() + PAYLOAD + payload);
  }
  @Override
  public WishDTO removeProductWishByCustomerId(Long customerId, Long productId) {
    var wish = this.findWishByCustomer(customerId);
    if(productService.productListIsPresent(wish.get().getProducts(), customerId)) {
      this.removeProductOnTheList(wish, customerId, productId);
      var updatedWish = wishDAO.findAndModify(wish.get());
      return WishDTO.of(updatedWish);
    }
    return WishDTO.of(wish.get());
  }

  private Optional<Wish> removeProductOnTheList(Optional<Wish> wish, Long customerId, Long productId){
    if(productService.productIsOnList(wish.get().getProducts(), productId)){
      wish.get().getProducts().removeIf(product -> product.getId().equals(productId));
      return wish;
    }
    throw new IllegalArgumentException(INFO_PRODUCT_NOT_ON_LIST + customerId);
  }
  @Override
  public WishDTO getWishByCustomerIdAndProductId(Long customerId, Long productId) {
    var wish = wishDAO.findByCustomerAndProduct(customerId, productId);
    var product = wish.getProducts().stream().filter(productFilter -> productFilter.getId().equals(productId)).findFirst().orElseThrow();
    wish.setProducts(List.of(product));
    return WishDTO.of(wish);
  }
}