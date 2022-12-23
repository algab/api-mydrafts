package br.com.mydrafts.apimydrafts.aspects;

import br.com.mydrafts.apimydrafts.exceptions.BusinessException;
import br.com.mydrafts.apimydrafts.services.JWTService;
import lombok.AllArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Aspect
@Component
@AllArgsConstructor
public class AuthorizeParamAspect {

    private final JWTService jwtService;

    @Before("@annotation(br.com.mydrafts.apimydrafts.annotations.AuthorizeParam)")
    public void authorizeParam(JoinPoint joinPoint) {
        try {
            var id = jwtService.getIdByToken();
            var param = joinPoint.getArgs()[0];
            if (!param.equals(id)) {
                throw new BusinessException(HttpStatus.UNAUTHORIZED.value(), HttpStatus.UNAUTHORIZED.toString(), HttpStatus.UNAUTHORIZED.toString());
            }
        } catch (Exception e) {
            throw new BusinessException(HttpStatus.UNAUTHORIZED.value(), HttpStatus.UNAUTHORIZED.toString(), HttpStatus.UNAUTHORIZED.toString());
        }
    }

}
