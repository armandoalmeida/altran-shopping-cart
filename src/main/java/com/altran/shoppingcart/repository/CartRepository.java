package com.altran.shoppingcart.repository;

import com.altran.shoppingcart.enumeration.CartStatusEnum;
import com.altran.shoppingcart.model.Cart;
import com.altran.shoppingcart.model.Item;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface CartRepository extends MongoRepository<Cart, ObjectId> {
    List<Cart> findByUserOrderByTotalAsc(String user);
    List<Cart> findByStatus(CartStatusEnum status);
}
