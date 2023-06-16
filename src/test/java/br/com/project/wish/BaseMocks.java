package br.com.project.wish;

import br.com.project.wish.dto.CustomerDTO;
import br.com.project.wish.dto.ProductDTO;
import br.com.project.wish.dto.WishDTO;
import br.com.project.wish.model.Customer;
import br.com.project.wish.model.Product;
import br.com.project.wish.model.Wish;
import com.google.common.collect.ImmutableList;

import java.util.ArrayList;
import java.util.List;

import static br.com.project.wish.constant.CommonConstant.baseMocksConstant.*;

public final class BaseMocks {
  public static WishDTO getWishDTO(Long id, CustomerDTO customer, List<ProductDTO> products) {
    WishDTO wish = new WishDTO();
    wish.setId(id);
    wish.setCustomer(customer);
    wish.setProducts(products);
    return wish;
  }
  public static CustomerDTO getCustomerDTO(Long id, String name, String email) {
    CustomerDTO customer = new CustomerDTO();
    customer.setId(id);
    customer.setName(name);
    customer.setEmail(email);
    return customer;
  }
  public static ProductDTO getProductDTO(Long id, String name, String description, double price) {
    ProductDTO product = new ProductDTO();
    product.setId(id);
    product.setName(name);
    product.setDescription(description);
    product.setPrice(price);
    return product;
  }
  public static List<ProductDTO> getProductListDTO(){
    ProductDTO product01 = BaseMocks.getProductDTO(1l, PRODUCT_NAME_01, PRODUCT_DESCRIPTION_01, PRODUCT_PRICE_01);
    ProductDTO product02 = BaseMocks.getProductDTO(2l, PRODUCT_NAME_02, PRODUCT_DESCRIPTION_02, PRODUCT_PRICE_02);
    ProductDTO product03 = BaseMocks.getProductDTO(3l, PRODUCT_NAME_03, PRODUCT_DESCRIPTION_03, PRODUCT_PRICE_03);
    ProductDTO product04 = BaseMocks.getProductDTO(4l, PRODUCT_NAME_04, PRODUCT_DESCRIPTION_04, PRODUCT_PRICE_04);

    List<ProductDTO> productList = ImmutableList.<ProductDTO>builder()
        .add(product01)
        .add(product02)
        .add(product03)
        .add(product04)
        .build();

    return productList;
  }
  public static Wish getWish(Long id, Customer customer, List<Product> products) {
    Wish wish = new Wish();
    wish.setId(id);
    wish.setCustomer(customer);
    wish.setProducts(products);
    return wish;
  }
  public static Customer getCustomer(Long id, String name, String email) {
    Customer customer = new Customer();
    customer.setId(id);
    customer.setName(name);
    customer.setEmail(email);
    return customer;
  }
  public static Product getProduct(Long id, String name, String description, double price) {
    Product product = new Product();
    product.setId(id);
    product.setName(name);
    product.setDescription(description);
    product.setPrice(price);
    return product;
  }
  public static List<Product> getProductList(){
    Product product01 = BaseMocks.getProduct(1l, PRODUCT_NAME_01, PRODUCT_DESCRIPTION_01, PRODUCT_PRICE_01);
    Product product02 = BaseMocks.getProduct(2l, PRODUCT_NAME_02, PRODUCT_DESCRIPTION_02, PRODUCT_PRICE_02);
    Product product03 = BaseMocks.getProduct(3l, PRODUCT_NAME_03, PRODUCT_DESCRIPTION_03, PRODUCT_PRICE_03);
    Product product04 = BaseMocks.getProduct(4l, PRODUCT_NAME_04, PRODUCT_DESCRIPTION_04, PRODUCT_PRICE_04);

    List<Product> productList = new ArrayList<>();

    productList.add(product01);
    productList.add(product02);
    productList.add(product03);
    productList.add(product04);

    return productList;
  }
}