package com.altran.shoppingcart.service;

import com.altran.shoppingcart.model.User;

import java.util.List;

public interface UserService extends CrudService<User> {
    List<User> findByEmail(String email);
}
