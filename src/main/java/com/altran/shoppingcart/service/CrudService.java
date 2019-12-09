package com.altran.shoppingcart.service;

import org.bson.types.ObjectId;

import java.util.List;
import java.util.Optional;

public interface CrudService<T> {
    List<T> findAll();

    T create(T t);

    Optional<T> getById(ObjectId id);

    void updateById(ObjectId id, T t);

    void delete(ObjectId id);
}
