package com.altran.shoppingcart.repository;


import static org.assertj.core.api.Assertions.assertThat;

import com.altran.shoppingcart.model.User;
import com.altran.shoppingcart.repository.UsersRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest
@DirtiesContext
public class UserRepositoryTest {

    @Autowired
    UsersRepository userRepository;

    @Test
    public void contextLoads() {
    }

    @Test
    public void givenUserObject_whenSave_thenCreateNewUser() {
        User user = new User();
        user.setName("John Doe");
        user.setEmail("john.doe@example.com");
        userRepository.save(user);

        assertThat(userRepository.findAll().size()).isGreaterThan(0);
    }


}