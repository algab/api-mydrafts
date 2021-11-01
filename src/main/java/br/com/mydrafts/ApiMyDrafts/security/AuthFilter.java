package br.com.mydrafts.ApiMyDrafts.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.crypto.SecretKey;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class AuthFilter extends OncePerRequestFilter {

    private String secret;

    public AuthFilter(String secret) {
        this.secret = secret;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        String authorization  = httpServletRequest.getHeader(HttpHeaders.AUTHORIZATION);
        String token = authorization.substring(7);
        SecretKey key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        String id = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody().getId();
        UsernamePasswordAuthenticationToken user = new UsernamePasswordAuthenticationToken(id, null, null);
        SecurityContextHolder.getContext().setAuthentication(user);
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }

}
