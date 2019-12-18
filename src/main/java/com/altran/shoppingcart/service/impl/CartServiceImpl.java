package com.altran.shoppingcart.service.impl;

import com.altran.shoppingcart.enumeration.CartStatusEnum;
import com.altran.shoppingcart.exception.DocumentNotFoundException;
import com.altran.shoppingcart.model.Cart;
import com.altran.shoppingcart.model.CartItem;
import com.altran.shoppingcart.model.Item;
import com.altran.shoppingcart.repository.CartRepository;
import com.altran.shoppingcart.repository.ItemRepository;
import com.altran.shoppingcart.security.util.SecurityUtil;
import com.altran.shoppingcart.service.CartService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;

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
        cart.setStatus(CartStatusEnum.OPENED);
        if (cart.getItems() == null)
            cart.setItems(new ArrayList<CartItem>());
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
        return repository.findByUserOrderByTotalAsc(user);
    }

    @Override
    public Cart getOpenCart(String user) {
        List<Cart> carts = repository.findByStatus(CartStatusEnum.OPENED);
        if (carts != null && !carts.isEmpty())
            return carts.get(0);

        return create(new Cart(new ArrayList<CartItem>()));
    }

    @Override
    public Cart addItemToCart(ObjectId id, String itemId) {
        Cart cart = getById(id).orElse(null);
        if (cart != null) {
            AtomicBoolean addNew = new AtomicBoolean(true);
            if (cart.getItems() != null) {
                cart.getItems().forEach(cartItem -> {
                    if (cartItem.getItem().getId().equals(itemId)) {
                        cartItem.setQuantity(cartItem.getQuantity() + 1);
                        addNew.set(false);
                    }
                });
            } else
                cart.setItems(new ArrayList<>());

            if (addNew.get()) {
                Optional<Item> item = itemRepository.findById(new ObjectId(itemId));
                item.ifPresent(i -> cart.getItems().add(new CartItem(item.get(), 1)));
            }

            updateById(id, cart);
            return cart;
        }
        throw new DocumentNotFoundException(Cart.class.getName());
    }


    @Override
    public Cart removeItemFromCart(ObjectId id, String itemId) {
        Cart cart = getById(id).orElse(null);
        if (cart != null && cart.getItems() != null) {
            Iterator<CartItem> i = cart.getItems().iterator();
            while (i.hasNext()) {
                CartItem item = i.next();
                if (item.getItem().getId().equals(itemId)) {
                    item.setQuantity(item.getQuantity() - 1);
                    if (item.getQuantity() == 0)
                        i.remove();
                    break;
                }
            }

            updateById(id, cart);
            return cart;
        }
        throw new DocumentNotFoundException(Cart.class.getName());
    }

    @Override
    public Cart removeAllItems(ObjectId id, String itemId) {
        Cart cart = getById(id).orElse(null);
        if (cart != null && cart.getItems() != null) {
            cart.getItems().removeIf(cartItem -> cartItem.getItem().getId().equals(itemId));
            updateById(id, cart);
            return cart;
        }
        throw new DocumentNotFoundException(Cart.class.getName());
    }

    @Override
    public Cart closeCart(ObjectId id) {
        Cart cart = getById(id).orElse(null);
        if (cart != null && cart.getItems() != null) {
            cart.setStatus(CartStatusEnum.CLOSED);
            updateById(id, cart);
            return cart;
        }
        throw new DocumentNotFoundException(Cart.class.getName());
    }

    private static Double getTotal(Cart cart) {
        if (cart.getItems() != null)
            return cart.getItems().stream()
                    .map(cartItem -> {
                        return cartItem.getItem().getValue() * cartItem.getQuantity();
                    })
                    .reduce(0d, Double::sum);
        return 0d;
    }
}
