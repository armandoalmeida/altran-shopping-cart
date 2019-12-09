package com.altran.shoppingcart.repository;

import com.altran.shoppingcart.model.Cart;
import com.altran.shoppingcart.model.Item;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CartRepository extends MongoRepository<Cart, ObjectId> {
}
