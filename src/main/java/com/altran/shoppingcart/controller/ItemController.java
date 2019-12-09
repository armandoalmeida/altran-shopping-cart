package com.altran.shoppingcart.controller;

import com.altran.shoppingcart.model.Item;
import com.altran.shoppingcart.service.ItemService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
public class ItemController {
    @Autowired
    private ItemService service;

    @GetMapping("/items")
    public List<Item> findAll() {
        return service.findAll();
    }

    @PostMapping("/items")
    @ResponseBody
    public Item create(@Valid @RequestBody Item item) {
        return service.create(item);
    }

    @GetMapping("/items/{id}")
    public Optional<Item> getById(@PathVariable("id") ObjectId id) {
        return service.getById(id);
    }

    @PutMapping("/items/{id}")
    public void updateById(@PathVariable("id") ObjectId id, @Valid @RequestBody Item item) {
        service.updateById(id, item);
    }

    @DeleteMapping("/items/{id}")
    public void delete(@PathVariable("id") ObjectId id) {
        service.delete(id);
    }
}
