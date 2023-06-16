package br.com.project.wish.dto;

import javax.validation.constraints.NotNull;

import static br.com.project.wish.constant.CommonConstant.logConstant.INFO_CANNOT_BE_NULL;

public class ProductIdDTO {
  @NotNull(message = INFO_CANNOT_BE_NULL)
  private Long productId;

  public Long getId() {
    return productId;
  }

  public void setProductId(Long productId) {
    this.productId = productId;
  }
}
