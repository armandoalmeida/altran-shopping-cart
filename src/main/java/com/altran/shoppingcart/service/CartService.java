package com.altran.shoppingcart.service;

import com.altran.shoppingcart.model.Cart;
import org.bson.types.ObjectId;

import java.util.List;

public interface CartService extends CrudService<Cart> {
    List<Cart> findByUser(String user);

    Cart getCartOpen(String user);

    Cart addItemToCart(ObjectId id, String itemId);
}
