package br.com.mydrafts.apimydrafts.aspects;

import br.com.mydrafts.apimydrafts.annotations.AuthorizeData;
import br.com.mydrafts.apimydrafts.exceptions.BusinessException;
import br.com.mydrafts.apimydrafts.repository.DraftRepository;
import br.com.mydrafts.apimydrafts.repository.FavoriteRepository;
import br.com.mydrafts.apimydrafts.services.JWTService;
import lombok.AllArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Aspect
@Component
@AllArgsConstructor
public class AuthorizeDataAspect {

    private final JWTService jwtService;

    private final DraftRepository draftRepository;

    private final FavoriteRepository favoriteRepository;

    @Before("@annotation(br.com.mydrafts.apimydrafts.annotations.AuthorizeData)")
    public void authorizeData(JoinPoint joinPoint) {
        try {
            MethodSignature signature = (MethodSignature) joinPoint.getSignature();
            AuthorizeData annotation = signature.getMethod().getAnnotation(AuthorizeData.class);
            var id = jwtService.getId();
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
