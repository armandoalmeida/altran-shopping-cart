package com.altran.shoppingcart.controller;

import com.altran.shoppingcart.model.Cart;
import com.altran.shoppingcart.service.CartService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class CartController {
    @Autowired
    private CartService service;

    @GetMapping("/carts")
    public List<Cart> findAll() {
        return service.findAll();
    }

    @PostMapping("/carts")
    @ResponseBody
    public Cart create(@Valid @RequestBody Cart cart) {
        return service.create(cart);
    }

    @GetMapping("/carts/{id}")
    public Cart getById(@PathVariable("id") ObjectId id) {
        return service.getById(id).orElse(null);
    }

    @PutMapping("/carts/{id}")
    public void updateById(@PathVariable("id") ObjectId id, @Valid @RequestBody Cart cart) {
        service.updateById(id, cart);
    }


    @DeleteMapping("/carts/{id}")
    public void delete(@PathVariable("id") ObjectId id) {
        service.delete(id);
    }

    @GetMapping("/carts/{userId}/all")
    public List<Cart> getByUser(@PathVariable("userId") String userId) {
        return service.findByUser(userId);
    }

    @GetMapping("/carts/{userId}/open")
    public Cart getOpenCart(@PathVariable("userId") String userId) {
        return service.getOpenCart(userId);
    }

    @PostMapping("/carts/{id}/item/{itemId}")
    public Cart addItemToCart(@PathVariable("id") ObjectId id, @PathVariable("itemId") String itemId) {
        return service.addItemToCart(id, itemId);
    }

    @DeleteMapping("/carts/{id}/item/{itemId}")
    public Cart removeItemFromCart(@PathVariable("id") ObjectId id, @PathVariable("itemId") String itemId) {
        return service.removeItemFromCart(id, itemId);
    }

    @DeleteMapping("/carts/{id}/all/{itemId}")
    public Cart removeAllItems(@PathVariable("id") ObjectId id, @PathVariable("itemId") String itemId) {
        return service.removeAllItems(id, itemId);
    }

    @PostMapping("/carts/{id}/close")
    public void closeCart(@PathVariable("id") ObjectId id) {
        service.closeCart(id);
    }

}
