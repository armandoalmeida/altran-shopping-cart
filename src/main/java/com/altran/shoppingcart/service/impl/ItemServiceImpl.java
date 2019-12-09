package com.altran.shoppingcart.service.impl;

import com.altran.shoppingcart.model.Item;
import com.altran.shoppingcart.repository.ItemRepository;
import com.altran.shoppingcart.service.ItemService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ItemServiceImpl implements ItemService {
    @Autowired
    private ItemRepository repository;

    @Override
    public List<Item> findAll() {
        return repository.findAll();
    }

    @Override
    public Item create(Item Item) {
        Item.set_id(ObjectId.get());
        repository.save(Item);
        return Item;
    }

    @Override
    public Optional<Item> getById(ObjectId id) {
        return repository.findById(id);
    }

    @Override
    public void updateById(ObjectId id, Item Item) {
        Item.set_id(id);
        repository.save(Item);
    }

    @Override
    public void delete(ObjectId id) {
        repository.delete(repository.findById(id).get());
    }
}
