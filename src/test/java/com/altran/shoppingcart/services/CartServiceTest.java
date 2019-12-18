package com.altran.shoppingcart.services;

import com.altran.shoppingcart.enumeration.CartStatusEnum;
import com.altran.shoppingcart.exception.UserMustBeUniqueException;
import com.altran.shoppingcart.model.Cart;
import com.altran.shoppingcart.model.CartItem;
import com.altran.shoppingcart.model.Item;
import com.altran.shoppingcart.model.User;
import com.altran.shoppingcart.repository.CartRepository;
import com.altran.shoppingcart.repository.UsersRepository;
import com.altran.shoppingcart.service.impl.CartServiceImpl;
import com.altran.shoppingcart.service.impl.UserServiceImpl;
import org.bson.types.ObjectId;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class CartServiceTest {

    @InjectMocks
    private CartServiceImpl service;

    @Mock
    private CartRepository repository;

    @Before
    public void before() {
        List<Cart> carts = Arrays.asList(
                Cart.builder()
                        ._id(new ObjectId("5df9b08807cf0b6d95b80bfb"))
                        .id("5df9b08807cf0b6d95b80bfb").total(10d).user("user1")
                        .items(Arrays.asList(
                                CartItem.builder().item(Item.builder().id("1").name("Item 1").value(5d).build()).quantity(1).build(),
                                CartItem.builder().item(Item.builder().id("2").name("Item 2").value(1d).build()).quantity(5).build()
                        ))
                        .build(),
                Cart.builder()
                        ._id(new ObjectId("5df9b07e07cf0b6d95b80bfa"))
                        .id("5df9b07e07cf0b6d95b80bfa").total(7d).user("user1")
                        .items(new ArrayList<CartItem>(Arrays.asList(
                                CartItem.builder().item(Item.builder().id("1").name("Item 1").value(5d).build()).quantity(1).build(),
                                CartItem.builder().item(Item.builder().id("2").name("Item 2").value(1d).build()).quantity(2).build()
                        )))
                        .build()
        );


        Mockito.doReturn(Optional.of(carts.get(0))).when(repository).findById(new ObjectId("5df9b08807cf0b6d95b80bfb"));
        Mockito.doReturn(Optional.of(carts.get(1))).when(repository).findById(new ObjectId("5df9b07e07cf0b6d95b80bfa"));
    }

    @Test
    public void givenCart_whenAddItemToCart_thenIncreaseQuantity() {
        Cart cart = service.addItemToCart(new ObjectId("5df9b08807cf0b6d95b80bfb"),"1");
        assertThat(cart.getItems().get(0).getQuantity()).isEqualTo(2);
    }

    @Test
    public void givenCart_whenAddItemToCart_thenIncreaseTotal() {
        Cart cart = service.addItemToCart(new ObjectId("5df9b08807cf0b6d95b80bfb"),"1");
        assertThat(cart.getTotal()).isEqualTo(15d);
    }

    @Test
    public void givenCart_whenRemoveItemFromCart_thenItemRemoved() {
        Cart cart = service.removeItemFromCart(new ObjectId("5df9b07e07cf0b6d95b80bfa"),"1");
        assertThat(cart.getItems().size()).isEqualTo(1);
    }

    @Test
    public void givenCart_whenCloseCart_thenCartIsClosed() {
        Cart cart = service.closeCart(new ObjectId("5df9b07e07cf0b6d95b80bfa"));
        assertThat(cart.getStatus()).isEqualTo(CartStatusEnum.CLOSED);
    }

}
