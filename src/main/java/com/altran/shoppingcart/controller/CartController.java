package com.altran.shoppingcart.controller;

import com.altran.shoppingcart.model.Cart;
import com.altran.shoppingcart.service.CartService;
import com.altran.shoppingcart.service.CartService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

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
    public Optional<Cart> getById(@PathVariable("id") ObjectId id) {
        return service.getById(id);
    }

    @PutMapping("/carts/{id}")
    public void updateById(@PathVariable("id") ObjectId id, @Valid @RequestBody Cart cart) {
        service.updateById(id, cart);
    }

    @DeleteMapping("/carts/{id}")
    public void delete(@PathVariable("id") ObjectId id) {
        service.delete(id);
    }

    @GetMapping("/carts/user/{id}")
    public List<Cart> getByUser(@PathVariable("email") String email) {
        return service.findByUser(email);
    }

    @GetMapping("/carts/opened/{id}")
    public Cart getCartsOpen(@PathVariable("email") String email) {
        return service.getCartOpen(email);
    }

    @PostMapping("/carts/{id}/add/{itemId}")
    public Cart addItemToCart(@PathVariable("id") ObjectId id, @PathVariable("itemId") String itemId) {
        return service.addItemToCart(id, itemId);
    }

}
