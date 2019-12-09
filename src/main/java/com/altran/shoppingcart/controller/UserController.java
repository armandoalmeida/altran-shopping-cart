package com.altran.shoppingcart.controller;

import com.altran.shoppingcart.model.User;
import com.altran.shoppingcart.service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
public class UserController {
    @Autowired
    private UserService service;

    @GetMapping("/users")
    public List<User> findAll() {
        return service.findAll();
    }

    @PostMapping("/users")
    @ResponseBody
    public User create(@Valid @RequestBody User user) {
        return service.create(user);
    }

    @GetMapping("/users/{id}")
    public Optional<User> getById(@PathVariable("id") ObjectId id) {
        return service.getById(id);
    }

    @PutMapping("/users/{id}")
    public void updateById(@PathVariable("id") ObjectId id, @Valid @RequestBody User user) {
        service.updateById(id, user);
    }

    @DeleteMapping("/users/{id}")
    public void delete(@PathVariable("id") ObjectId id) {
        service.delete(id);
    }
}
