package com.altran.shoppingcart.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

public class JwtTokenAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtConfig jwtConfig;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        String token = getToken(request);

        try {

            Claims claims = Jwts.parser().setSigningKey(jwtConfig.getSecret()).parseClaimsJws(token).getBody();

            if (this.validateTokenExpiration(claims)) {

                String username = claims.getSubject();
                if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                    UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
        } catch (Exception e) {
            // nothing
            e.printStackTrace();
        }

        chain.doFilter(request, response);
    }

    private String getToken(HttpServletRequest request) {
        String token = request.getHeader(this.jwtConfig.getHeader());
        if (token != null && token.startsWith(this.jwtConfig.getPrefix())) {
            token = token.substring(7);
        }
        return token;
    }

    private boolean validateTokenExpiration(Claims claims) {
        Date expiration = claims.getExpiration();
        if (expiration != null)
            return expiration.after(new Date());
        return false;
    }
}
