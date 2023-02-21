package br.com.mydrafts.apimydrafts.services;

import br.com.mydrafts.apimydrafts.constants.MyDraftsMessage;
import br.com.mydrafts.apimydrafts.dto.UserDTO;
import br.com.mydrafts.apimydrafts.exceptions.BusinessException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class JWTServiceImpl implements JWTService {

    private final JwtBuilder jwtBuilder;

    private final JwtParserBuilder jwtParserBuilder;

    private final HttpServletRequest request;

    @Value("${secret.jwt}")
    private String secret;

    @Override
    public String getIdByToken() {
        var token = getToken();
        var key = getKey();
        return jwtParserBuilder.setSigningKey(key).build().parseClaimsJws(token).getBody().getId();
    }

    @Override
    public String generateToken(UserDTO user) {
        SecretKey key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        return jwtBuilder.setId(user.getId()).setSubject(String.format("%s %s", user.getFirstName(), user.getLastName()))
            .setIssuedAt(new Date())
            .setExpiration(Date.from(LocalDateTime.now().plusDays(1L).atZone(ZoneId.systemDefault()).toInstant()))
            .signWith(key, SignatureAlgorithm.HS256)
            .compact();
    }

    @Override
    public boolean validateToken() {
        try {
            var token = getToken();
            var key = getKey();
            jwtParserBuilder.setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (JwtException exception) {
            throw new BusinessException(
                HttpStatus.UNAUTHORIZED.value(),
                HttpStatus.UNAUTHORIZED.getReasonPhrase(),
                MyDraftsMessage.TOKEN_INVALID
            );
        }
    }

    private String getToken() {
        return request.getHeader("Authorization").substring(7);
    }

    private SecretKey getKey() {
        return Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

}
