package br.com.mydrafts.apimydrafts.aspects;

import br.com.mydrafts.apimydrafts.annotations.AuthorizeBody;
import br.com.mydrafts.apimydrafts.dto.DraftFormDTO;
import br.com.mydrafts.apimydrafts.dto.FavoriteFormDTO;
import br.com.mydrafts.apimydrafts.exceptions.BusinessException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;

import static java.util.Objects.requireNonNull;

@Aspect
@Component
public class AuthorizeBodyAspect {

    @Value("${secret.jwt}")
    private String secret;

    @Before("@annotation(br.com.mydrafts.apimydrafts.annotations.AuthorizeBody)")
    public void authorizeBody(JoinPoint joinPoint) {
        try {
            MethodSignature signature = (MethodSignature) joinPoint.getSignature();
            AuthorizeBody annotation = signature.getMethod().getAnnotation(AuthorizeBody.class);
            HttpServletRequest request = ((ServletRequestAttributes) requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
            var token = request.getHeader("Authorization").substring(7);
            var key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
            var id = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody().getId();
            if (annotation.clazz().equals("Draft")) {
                var body = (DraftFormDTO) joinPoint.getArgs()[0];
                validateBody(body.getUserID(), id);
            } else if (annotation.clazz().equals("Favorite")) {
                var body = (FavoriteFormDTO) joinPoint.getArgs()[0];
                validateBody(body.getUserID(), id);
            }
        } catch (Exception e) {
            throw new BusinessException(HttpStatus.UNAUTHORIZED.value(), HttpStatus.UNAUTHORIZED.toString(), HttpStatus.UNAUTHORIZED.toString());
        }
    }

    private void validateBody(String userID, String id) {
        if (!userID.equals(id)) {
            throw new BusinessException(HttpStatus.UNAUTHORIZED.value(), HttpStatus.UNAUTHORIZED.toString(), HttpStatus.UNAUTHORIZED.toString());
        }
    }

}
