package com.altran.shoppingcart.controller;

import com.altran.shoppingcart.security.jwt.JwtToken;
import com.altran.shoppingcart.model.User;
import com.altran.shoppingcart.repository.UsersRepository;
import com.altran.shoppingcart.security.jwt.JwtConfig;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtConfig jwtConfig;

    @PostMapping
    public JwtToken createJWT(@Valid @RequestBody User user)
            throws AuthenticationException {

        Authentication authentication = authenticationManager.authenticate( //
                new UsernamePasswordAuthenticationToken(
                        user.getEmail(), user.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        Map<String, Object> claims = new HashMap<>();
        claims.put("sub", userDetails.getUsername());
        if (userDetails.getAuthorities() != null)
            userDetails.getAuthorities().forEach(authority -> claims.put("role", authority.getAuthority()));
        claims.put("created", new Date());

        Key key = Keys.hmacShaKeyFor(jwtConfig.getSecret().getBytes());

        String token = Jwts.builder()
                .setClaims(claims)
                .setExpiration(getExpirationTime())
                .signWith(key).compact();

        return new JwtToken(token);
    }

    private Date getExpirationTime() {
        return new Date(System.currentTimeMillis() + jwtConfig.getExpiration() * 1000);
    }
}
