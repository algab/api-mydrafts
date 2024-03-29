package br.com.mydrafts.apimydrafts.aspects;

import br.com.mydrafts.apimydrafts.annotations.AuthorizeBody;
import br.com.mydrafts.apimydrafts.dto.DraftFormDTO;
import br.com.mydrafts.apimydrafts.dto.FavoriteFormDTO;
import br.com.mydrafts.apimydrafts.exceptions.BusinessException;
import br.com.mydrafts.apimydrafts.services.JWTService;
import lombok.AllArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import static br.com.mydrafts.apimydrafts.constants.MyDraftsMessage.USER_UNAUTHORIZED;

@Aspect
@Component
@AllArgsConstructor
public class AuthorizeBodyAspect {

    private final JWTService jwtService;

    @Before("@annotation(br.com.mydrafts.apimydrafts.annotations.AuthorizeBody)")
    public void authorizeBody(JoinPoint joinPoint) {
        try {
            MethodSignature signature = (MethodSignature) joinPoint.getSignature();
            AuthorizeBody annotation = signature.getMethod().getAnnotation(AuthorizeBody.class);
            var id = jwtService.getIdByToken();
            if (annotation.value().equals("Draft")) {
                var body = (DraftFormDTO) joinPoint.getArgs()[0];
                validateBody(body.getUserID(), id);
            } else {
                var body = (FavoriteFormDTO) joinPoint.getArgs()[0];
                validateBody(body.getUserID(), id);
            }
        } catch (Exception e) {
            throw new BusinessException(
                HttpStatus.UNAUTHORIZED.value(),
                HttpStatus.UNAUTHORIZED.getReasonPhrase(),
                USER_UNAUTHORIZED
            );
        }
    }

    private void validateBody(String userID, String id) {
        if (!userID.equals(id)) {
            throw new BusinessException(
                HttpStatus.UNAUTHORIZED.value(),
                HttpStatus.UNAUTHORIZED.getReasonPhrase(),
                USER_UNAUTHORIZED
            );
        }
    }

}
