package com.altran.shoppingcart.repository;

import com.altran.shoppingcart.model.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface UsersRepository extends MongoRepository<User, ObjectId> {
    List<User> findByEmail(String email);
}
