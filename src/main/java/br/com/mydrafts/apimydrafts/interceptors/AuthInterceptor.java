package br.com.mydrafts.apimydrafts.interceptors;

import br.com.mydrafts.apimydrafts.exceptions.BusinessException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.crypto.SecretKey;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.nio.charset.StandardCharsets;

import static java.util.Objects.nonNull;

@Component
public class AuthInterceptor implements HandlerInterceptor {

    @Value("${secret.jwt}")
    private String secret;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        try {
            var authorization = request.getHeader("Authorization");
            if (nonNull(authorization)) {
                String token = authorization.substring(7);
                SecretKey key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
                if (!validateToken(token, key)) {
                    throw new BusinessException(HttpStatus.UNAUTHORIZED.value(), HttpStatus.UNAUTHORIZED.toString(), HttpStatus.UNAUTHORIZED.toString());
                }
                return true;
            }
            return false;
        } catch (Exception e) {
            throw new BusinessException(HttpStatus.UNAUTHORIZED.value(), HttpStatus.UNAUTHORIZED.toString(), HttpStatus.UNAUTHORIZED.toString());
        }
    }

    private boolean validateToken(String token, SecretKey key) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (JwtException exception) {
            return false;
        }
    }

}
