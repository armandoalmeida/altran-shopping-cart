package com.altran.shoppingcart.repository;


import com.altran.shoppingcart.model.Item;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.AutoConfigureDataMongo;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;


@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureDataMongo
public class ItemRepositoryTest {

    @Autowired
    ItemRepository repository;

    @Before
    public void before() {
        repository.save(Item.builder().name("Item 1").value(1d).build());
    }

    @Test
    public void givenObject_whenFindAll_thenListIsNotEmpty() {
        assertThat(repository.findAll().size()).isGreaterThan(0);
    }

}