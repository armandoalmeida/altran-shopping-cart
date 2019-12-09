package com.altran.shoppingcart.controller;

import com.altran.shoppingcart.model.User;
import com.altran.shoppingcart.repository.UsersRepository;
import com.altran.shoppingcart.security.JwtConfig;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
//@CrossOrigin(origins = "*")
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
    public String createJWT(@Valid @RequestBody User user)
            throws AuthenticationException {

        Authentication authentication = authenticationManager.authenticate( //
                new UsernamePasswordAuthenticationToken(
                        user.getEmail(), user.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserDetails userDetails = (UserDetails) authentication.getPrincipal(); //userDetailsService.loadUserByUsername(user.getEmail());

        Map<String, Object> claims = new HashMap<>();
        claims.put("sub", userDetails.getUsername());
        if (userDetails.getAuthorities() != null)
            userDetails.getAuthorities().forEach(authority -> claims.put("role", authority.getAuthority()));
        claims.put("created", new Date());

        String token = Jwts.builder().setClaims(claims).setExpiration(getExpirationTime())
                .signWith(SignatureAlgorithm.HS512, jwtConfig.getSecret()).compact();

        return token;
    }

    private Date getExpirationTime() {
        return new Date(System.currentTimeMillis() + jwtConfig.getExpiration() * 1000);
    }
}
