package br.com.project.wish.dao;

import br.com.project.wish.handler.ResourceNotFoundException;
import br.com.project.wish.helper.StringHelper;
import br.com.project.wish.model.Wish;
import com.mongodb.MongoException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import java.util.Optional;

import static br.com.project.wish.constant.CommonConstant.logConstant.*;
import static br.com.project.wish.constant.CommonConstant.queryConstant.PRODUCTS;

@Component
public class WishDAO extends Attributes {
  private static final Logger LOGGER = LoggerFactory.getLogger(WishDAO.class);
  @Autowired
  private MongoTemplate mongoTemplate;
  public Optional<Wish> findById(Long wishId){
    try {
      var result = mongoTemplate.findById(wishId, Wish.class);
      if(result==null) return Optional.empty();
      return Optional.of(result);
    } catch (MongoException e) {
      LOGGER.error(ERROR_QUERY_WISH_BY_CUSTOMER_ID_LOG, wishId.toString(), e.getLocalizedMessage());
      throw new MongoException(ERROR_QUERY_WISH_BY_CUSTOMER_ID + wishId.toString() + ERROR + e.getLocalizedMessage());
    }
  }

  public Wish findAndModify(Wish wish){
    Query query = new Query();
    query.addCriteria(this.setWishId(wish.getId()));
    Update update = new Update();
    update.set(PRODUCTS, wish.getProducts());
    FindAndModifyOptions options = new FindAndModifyOptions().returnNew(true).upsert(false);
    try {
      return mongoTemplate.findAndModify(query, update, options, Wish.class);
    } catch (MongoException e) {
      var queryString = StringHelper.convertObjectTo(query.getQueryObject());
      var updateString = StringHelper.convertObjectTo(update.getUpdateObject());
      var wishString = StringHelper.convertObjectTo(wish);
      LOGGER.error(ERROR_UPDATE_PRODUCT_LIST_CUSTOMER_ID_LOG, wishString, queryString, updateString, e.getLocalizedMessage());
      throw new MongoException(ERROR_UPDATE_PRODUCT_LIST_CUSTOMER_ID + wishString + QUERY + queryString + UPDATE + updateString + ERROR + e.getLocalizedMessage());
    }
  }
  public Wish findByCustomerAndProduct(Long wishId, Long productId){
    Query query = new Query();
    query.addCriteria(this.setWishIdAndProductId(wishId, productId));
    var queryString = StringHelper.convertObjectTo(query.getQueryObject());
    try {
      return mongoTemplate.findOne(query, Wish.class);
    } catch (NullPointerException e) {
      throw new ResourceNotFoundException(INFO_PRODUCT_NOT_FOUND_BY_CUSTOMERID + wishId + PRODUCT_ID + productId + QUERY + queryString + ERROR + e.getLocalizedMessage());
    } catch (MongoException e) {
      throw new MongoException(ERROR_PRODUCT_NOT_FOUND_BY_CUSTOMERID + wishId + PRODUCT_ID + productId + QUERY + queryString + ERROR + e.getLocalizedMessage());
    }
  }
}