package com.altran.shoppingcart.service.impl;

import com.altran.shoppingcart.exception.UserMustBeUniqueException;
import com.altran.shoppingcart.model.User;
import com.altran.shoppingcart.repository.UsersRepository;
import com.altran.shoppingcart.service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UsersRepository repository;

    @Override
    public List<User> findAll() {
        return repository.findAll();
    }

    @Override
    public User create(User user) {
        List<User> users = repository.findByEmail(user.getEmail());

        if (users != null && !users.isEmpty())
            throw new UserMustBeUniqueException(user.getEmail());

        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        user.set_id(ObjectId.get());
        user.setId(user.get_id().toString());
        repository.save(user);
        return user;
    }

    @Override
    public Optional<User> getById(ObjectId id) {
        return repository.findById(id);
    }

    @Override
    public void updateById(ObjectId id, User user) {
        user.set_id(id);
        user.setId(user.get_id().toString());
        repository.save(user);
    }

    @Override
    public void delete(ObjectId id) {
        repository.delete(repository.findById(id).get());
    }

    @Override
    public List<User> findByEmail(String email) {
        return this.repository.findByEmail(email);
    }
}
