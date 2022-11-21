package br.com.mydrafts.apimydrafts.aspects;

import br.com.mydrafts.apimydrafts.annotations.AuthorizeData;
import br.com.mydrafts.apimydrafts.exceptions.BusinessException;
import br.com.mydrafts.apimydrafts.repository.DraftRepository;
import br.com.mydrafts.apimydrafts.repository.FavoriteRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
public class AuthorizeDataAspect {

    private final DraftRepository draftRepository;

    private final FavoriteRepository favoriteRepository;

    @Value("${secret.jwt}")
    private String secret;

    @Before("@annotation(br.com.mydrafts.apimydrafts.annotations.AuthorizeData)")
    public void authorizeData(JoinPoint joinPoint) {
        try {
            MethodSignature signature = (MethodSignature) joinPoint.getSignature();
            AuthorizeData annotation = signature.getMethod().getAnnotation(AuthorizeData.class);
            HttpServletRequest request = ((ServletRequestAttributes) requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
            var token = request.getHeader("Authorization").substring(7);
            var key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
            var id = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody().getId();
            if (annotation.clazz().equals("Draft")) {
                var idDraft = (String) joinPoint.getArgs()[0];
                validateDraft(idDraft, id);
            } else if (annotation.clazz().equals("Favorite")) {
                var idFavorite = (String) joinPoint.getArgs()[0];
                validateFavorite(idFavorite, id);
            }
        } catch (Exception e) {
            throw new BusinessException(HttpStatus.UNAUTHORIZED.value(), HttpStatus.UNAUTHORIZED.toString(), HttpStatus.UNAUTHORIZED.toString());
        }
    }

    private void validateDraft(String idDraft, String id) {
        var draft = draftRepository.findById(idDraft);
        if (draft.isPresent()) {
            var idUser = draft.get().getUser().getId();
            if (!idUser.equals(id)) {
                throw new BusinessException(HttpStatus.UNAUTHORIZED.value(), HttpStatus.UNAUTHORIZED.toString(), HttpStatus.UNAUTHORIZED.toString());
            }
        }
        throw new BusinessException(HttpStatus.UNAUTHORIZED.value(), HttpStatus.UNAUTHORIZED.toString(), HttpStatus.UNAUTHORIZED.toString());
    }

    private void validateFavorite(String idFavorite, String id) {
        var favorite = favoriteRepository.findById(idFavorite);
        if (favorite.isPresent()) {
            var idUser = favorite.get().getUser().getId();
            if (!idUser.equals(id)) {
                throw new BusinessException(HttpStatus.UNAUTHORIZED.value(), HttpStatus.UNAUTHORIZED.toString(), HttpStatus.UNAUTHORIZED.toString());
            }
        }
        throw new BusinessException(HttpStatus.UNAUTHORIZED.value(), HttpStatus.UNAUTHORIZED.toString(), HttpStatus.UNAUTHORIZED.toString());
    }

}
