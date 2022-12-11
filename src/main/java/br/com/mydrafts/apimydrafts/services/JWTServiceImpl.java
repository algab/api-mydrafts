package br.com.mydrafts.apimydrafts.services;

import br.com.mydrafts.apimydrafts.exceptions.BusinessException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.JwtParserBuilder;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;

@Component
@RequiredArgsConstructor
public class JWTServiceImpl implements JWTService {

    private final JwtParserBuilder jwt;

    private final HttpServletRequest request;

    @Value("${secret.jwt}")
    private String secret;

    @Override
    public String getId() {
        var token = getToken();
        var key = getKey();
        return jwt.setSigningKey(key).build().parseClaimsJws(token).getBody().getId();
    }

    @Override
    public boolean validateToken() {
        try {
            var token = getToken();
            var key = getKey();
            jwt.setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (JwtException exception) {
            throw new BusinessException(HttpStatus.UNAUTHORIZED.value(), HttpStatus.UNAUTHORIZED.toString(), HttpStatus.UNAUTHORIZED.toString());
        }
    }

    private String getToken() {
        return request.getHeader("Authorization").substring(7);
    }

    private SecretKey getKey() {
        return Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

}
