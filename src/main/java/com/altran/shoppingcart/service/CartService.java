package com.altran.shoppingcart.service;

import com.altran.shoppingcart.model.Cart;
import org.bson.types.ObjectId;

import java.util.List;

public interface CartService extends CrudService<Cart> {
    void updateById(ObjectId id, Cart cart);

    List<Cart> findByUser(String user);

    Cart getOpenCart(String user);

    Cart addItemToCart(ObjectId id, String itemId);

    Cart removeItemFromCart(ObjectId id, String itemId);

    Cart removeAllItems(ObjectId id, String itemId);
}
