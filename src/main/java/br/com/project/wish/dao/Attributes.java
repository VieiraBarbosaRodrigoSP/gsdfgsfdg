package br.com.project.wish.dao;

import org.springframework.data.mongodb.core.query.Criteria;

import static br.com.project.wish.constant.CommonConstant.queryConstant.ID;
import static br.com.project.wish.constant.CommonConstant.queryConstant.PRODUCTS_ID;

public abstract class Attributes {
  public Criteria setWishId(Long wishId) { return Criteria.where(ID).is(wishId); }
  public Criteria setProductId(Long productId) {
    return Criteria.where(PRODUCTS_ID).is(productId);
  }
  public Criteria setWishIdAndProductId(Long wishId, Long productId){
    var criteria = new Criteria();
    criteria.andOperator( this.setWishId(wishId), this.setProductId(productId));
    return criteria;
  }
}