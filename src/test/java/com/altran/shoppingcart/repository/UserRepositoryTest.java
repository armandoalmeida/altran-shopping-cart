package com.altran.shoppingcart.repository;


import com.altran.shoppingcart.model.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.AutoConfigureDataMongo;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureDataMongo
public class UserRepositoryTest {

    @Autowired
    UsersRepository userRepository;

    @Before
    public void before() {
        User user = new User();
        user.setName("John Doe");
        user.setEmail("john.doe@example.com");
        userRepository.save(user);
    }

    @Test
    public void givenUserObject_whenSave_thenCreateNewUser() {
        assertThat(userRepository.findAll().size()).isGreaterThan(0);
    }

    @Test
    public void givenUserObject_whenFindByEmail_thenReturnsTheUser() {
        List<User> users = userRepository.findByEmail("john.doe@example.com");
        assertThat(users.size()).isGreaterThan(0);
    }

    @Test
    public void givenUserObject_whenSave_thenUpdateData() {
        List<User> users = userRepository.findByEmail("john.doe@example.com");
        User user = users.get(0);
        user.setName("John Doe Doe");
        userRepository.save(user);

        users = userRepository.findByEmail("john.doe@example.com");
        assertThat(users.size()).isGreaterThan(0);
        assertThat(users.get(0).getName()).isEqualTo("John Doe Doe");
    }

    @Test
    public void givenUserObject_whenDelete_thenUpdateDatabase() {
        List<User> users = userRepository.findByEmail("john.doe@example.com");
        for (User u : users) {
            userRepository.deleteById(u.get_id());
        }

        users = userRepository.findByEmail("john.doe@example.com");
        assertThat(users.size()).isEqualTo(0);
    }
}