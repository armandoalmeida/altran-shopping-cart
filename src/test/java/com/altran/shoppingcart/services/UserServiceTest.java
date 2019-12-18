package com.altran.shoppingcart.services;

import com.altran.shoppingcart.exception.UserMustBeUniqueException;
import com.altran.shoppingcart.model.User;
import com.altran.shoppingcart.repository.UsersRepository;
import com.altran.shoppingcart.service.UserService;
import com.altran.shoppingcart.service.impl.UserServiceImpl;
import org.bson.types.ObjectId;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.AutoConfigureDataMongo;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class UserServiceTest {

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UsersRepository repository;

    @Before
    public void before() {
        User user = new User();
        user.setName("John Doe");
        user.setEmail("john.doe@example.com");
        user.setPassword("bla");
        List<User> users = Collections.singletonList(user);
        Mockito.doReturn(users).when(repository).findAll();
        Mockito.doReturn(users).when(repository).findByEmail("john.doe@example.com");
    }

    @Test
    public void givenUserObject_whenFindAll_thenReturnsAllUsers() {
        List<User> actualUsers = userService.findAll();

        User user = new User();
        user.setName("John Doe");
        user.setEmail("john.doe@example.com");
        user.setPassword("bla");
        List<User> users = Collections.singletonList(user);

        assertThat(actualUsers).isEqualTo(users);
    }

    @Test
    public void givenUserObject_whenCreate_thenCreateNewUser() {
        User usertoSave = User.builder().email("john.doe2@example.com").password("bla").build();
        User user = userService.create(usertoSave);
        assertThat(user.getEmail()).isEqualTo("john.doe2@example.com");
        assertThat(user.get_id()).isNotNull();
    }

    @Test(expected = UserMustBeUniqueException.class)
    public void givenExistentUser_whenCreate_thenThrowsException() {
        User usertoSave = User.builder().email("john.doe@example.com").password("bla").build();
        userService.create(usertoSave);
    }

}
