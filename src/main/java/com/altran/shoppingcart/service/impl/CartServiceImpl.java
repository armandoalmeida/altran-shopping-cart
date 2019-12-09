package com.altran.shoppingcart.service.impl;

import com.altran.shoppingcart.enumeration.CartStatusEnum;
import com.altran.shoppingcart.model.Cart;
import com.altran.shoppingcart.repository.CartRepository;
import com.altran.shoppingcart.security.util.SecurityUtil;
import com.altran.shoppingcart.service.CartService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CartServiceImpl implements CartService {
    @Autowired
    private CartRepository repository;

    @Override
    public List<Cart> findAll() {
        return repository.findAll();
    }

    @Override
    public Cart create(Cart cart) {
        cart.set_id(ObjectId.get());
        cart.setId(cart.get_id().toString());
        cart.setUser(SecurityUtil.getCurrentUserName());
        cart.setStatus(CartStatusEnum.OPEN);
        repository.save(cart);
        return cart;
    }

    @Override
    public Optional<Cart> getById(ObjectId id) {
        return repository.findById(id);
    }

    @Override
    public void updateById(ObjectId id, Cart cart) {
        cart.set_id(id);
        cart.setId(cart.get_id().toString());
        repository.save(cart);
    }

    @Override
    public void delete(ObjectId id) {
        repository.delete(repository.findById(id).get());
    }

    @Override
    public List<Cart> findByUser(String user) {
        return repository.findByUser(user);
    }

    @Override
    public Cart getCartOpen(String user) {
        List<Cart> carts = findByUser(user);
        for (Cart cart : carts) {
            if (CartStatusEnum.OPEN.equals(cart.getStatus()))
                return cart;
        }
        return null;
    }

    @Override
    public Cart addItemToCart(ObjectId id, String itemId) {
        Optional<Cart> cart = getById(id);
        if (cart.isPresent()) {
            Cart c = cart.get();
            if (c.getItems() == null)
                c.setItems(new ArrayList<>());
            c.getItems().add(itemId);

            updateById(id, c);
            return c;
        }
        return null;
    }
}
