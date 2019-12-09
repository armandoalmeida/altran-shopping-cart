package com.altran.shoppingcart.controller;

import com.altran.shoppingcart.model.User;
import com.altran.shoppingcart.repository.UsersRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
public class UserController {
    @Autowired
    private UsersRepository usersRepository;

    @GetMapping("/users")
    public List<User> findAll() {
        return usersRepository.findAll();
    }

    @PostMapping("/users")
    public User create(@Valid @RequestBody User user) {
        user.set_id(ObjectId.get());
        usersRepository.save(user);
        return user;
    }

    @GetMapping("/users/{id}")
    public Optional<User> getById(@PathVariable("id") ObjectId id) {
        return usersRepository.findById(id);
    }

    @PutMapping("/users/{id}")
    public void updateById(@PathVariable("id") ObjectId id, @Valid @RequestBody User user) {
        user.set_id(id);
        usersRepository.save(user);
    }

    @DeleteMapping("/users/{id}")
    public void delete(@PathVariable("id") ObjectId id) {
        usersRepository.delete(usersRepository.findById(id).get());
    }
}
