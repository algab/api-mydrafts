package br.com.mydrafts.apimydrafts.aspects;

import br.com.mydrafts.apimydrafts.exceptions.BusinessException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
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
public class AuthorizeParamAspect {

    @Value("${secret.jwt}")
    private String secret;

    @Before("@annotation(br.com.mydrafts.apimydrafts.annotations.AuthorizeParam)")
    public void authorizeParam(JoinPoint joinPoint) {
        try {
            HttpServletRequest request = ((ServletRequestAttributes) requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
            var token = request.getHeader("Authorization").substring(7);
            var key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
            var param = joinPoint.getArgs()[0];
            var id = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody().getId();
            if (!param.equals(id)) {
                throw new BusinessException(HttpStatus.UNAUTHORIZED.value(), HttpStatus.UNAUTHORIZED.toString(), HttpStatus.UNAUTHORIZED.toString());
            }
        } catch (Exception e) {
            throw new BusinessException(HttpStatus.UNAUTHORIZED.value(), HttpStatus.UNAUTHORIZED.toString(), HttpStatus.UNAUTHORIZED.toString());
        }
    }

}
