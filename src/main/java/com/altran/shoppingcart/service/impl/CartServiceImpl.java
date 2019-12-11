package com.altran.shoppingcart.service.impl;

import com.altran.shoppingcart.enumeration.CartStatusEnum;
import com.altran.shoppingcart.model.Cart;
import com.altran.shoppingcart.model.Item;
import com.altran.shoppingcart.repository.CartRepository;
import com.altran.shoppingcart.repository.ItemRepository;
import com.altran.shoppingcart.security.util.SecurityUtil;
import com.altran.shoppingcart.service.CartService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@Service
public class CartServiceImpl implements CartService {
    @Autowired
    private CartRepository repository;

    @Autowired
    private ItemRepository itemRepository;

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
        cart.setTotal(getTotal(cart));
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
        cart.setTotal(getTotal(cart));
        repository.save(cart);
    }

    @Override
    public void delete(ObjectId id) {
        repository.findById(id).ifPresent(i -> repository.delete(i));
    }

    @Override
    public List<Cart> findByUser(String user) {
        return repository.findByUser(user);
    }

    @Override
    public Cart getOpenCart(String user) {
        List<Cart> carts = findByUser(user);
        for (Cart cart : carts) {
            if (CartStatusEnum.OPEN.equals(cart.getStatus()))
                return cart;
        }
        return null;
    }

    @Override
    public Cart addItemToCart(ObjectId id, String itemId) {
        Cart cart = getById(id).orElse(null);
        if (cart != null) {
            if (cart.getItems() == null)
                cart.setItems(new ArrayList<>());
            Optional<Item> item = itemRepository.findById(new ObjectId(itemId));
            item.ifPresent(i -> cart.getItems().add(i));

            updateById(id, cart);
            return cart;
        }
        return null;
    }

    @Override
    public Cart removeItemFromCart(ObjectId id, String itemId) {
        Cart cart = getById(id).orElse(null);
        if (cart != null) {
            if (cart.getItems() != null) {
                Iterator<Item> i = cart.getItems().iterator();
                while (i.hasNext()) {
                    Item item = i.next();
                    if (item.getId().equals(itemId)) {
                        i.remove();
                        break;
                    }
                }
            }

            updateById(id, cart);
            return cart;
        }
        return null;
    }

    private static BigDecimal getTotal(Cart cart) {
        if (cart.getItems() != null)
            return cart.getItems().stream()
                    .map(Item::getValue)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
        return null;
    }
}
