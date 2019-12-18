package com.altran.shoppingcart.repository;


import com.altran.shoppingcart.model.Cart;
import com.altran.shoppingcart.model.CartItem;
import com.altran.shoppingcart.model.Item;
import org.bson.types.ObjectId;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.AutoConfigureDataMongo;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureDataMongo
public class CartRepositoryTest {

    @Autowired
    CartRepository repository;

    @Before
    public void before() {
        repository.save(Cart.builder()._id(new ObjectId("5df9b07e07cf0b6d95b80bfa"))
                .id("5df9b07e07cf0b6d95b80bfa").total(15d).user("user1")
                .items(Arrays.asList(
                        CartItem.builder().item(Item.builder().name("Item 1").value(5d).build()).quantity(2).build(),
                        CartItem.builder().item(Item.builder().name("Item 2").value(1d).build()).quantity(5).build()
                ))
                .build());
        repository.save(Cart.builder()._id(new ObjectId("5df9b08807cf0b6d95b80bfb"))
                .id("5df9b08807cf0b6d95b80bfb").total(10d).user("user1")
                .items(Arrays.asList(
                        CartItem.builder().item(Item.builder().name("Item 1").value(5d).build()).quantity(1).build(),
                        CartItem.builder().item(Item.builder().name("Item 2").value(1d).build()).quantity(5).build()
                ))
                .build());
    }

    @Test
    public void givenData_whenFindAll_thenListIsNotEmpty() {
        assertThat(repository.findAll().size()).isGreaterThan(0);
    }

    @Test
    public void givenData_whenFindByUserOrderByTotalAsc_thenOrderedList() {
        List<Cart> carts = Arrays.asList(
                Cart.builder()
                        ._id(new ObjectId("5df9b08807cf0b6d95b80bfb"))
                        .id("5df9b08807cf0b6d95b80bfb").total(10d).user("user1")
                        .items(Arrays.asList(
                                CartItem.builder().item(Item.builder().name("Item 1").value(5d).build()).quantity(1).build(),
                                CartItem.builder().item(Item.builder().name("Item 2").value(1d).build()).quantity(5).build()
                        ))
                        .build(),
                Cart.builder()
                        ._id(new ObjectId("5df9b07e07cf0b6d95b80bfa"))
                        .id("5df9b07e07cf0b6d95b80bfa").total(15d).user("user1")
                        .items(Arrays.asList(
                                CartItem.builder().item(Item.builder().name("Item 1").value(5d).build()).quantity(2).build(),
                                CartItem.builder().item(Item.builder().name("Item 2").value(1d).build()).quantity(5).build()
                        ))
                        .build()
        );

        assertThat(repository.findByUserOrderByTotalAsc("user1")).isEqualTo(carts);
    }

}