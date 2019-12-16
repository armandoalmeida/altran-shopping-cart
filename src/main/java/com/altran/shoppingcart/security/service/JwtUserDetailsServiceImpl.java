package com.altran.shoppingcart.security.service;

import com.altran.shoppingcart.model.User;
import com.altran.shoppingcart.repository.UsersRepository;
import com.altran.shoppingcart.security.jwt.JwtUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JwtUserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UsersRepository usersRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        List<User> users = usersRepository.findByEmail(email);

        if (users == null || users.isEmpty())
            throw new UsernameNotFoundException("User not found");

        User user = users.get(0);
        return new JwtUserDetails(user.getId(), user.getPassword(), null);
    }
}
