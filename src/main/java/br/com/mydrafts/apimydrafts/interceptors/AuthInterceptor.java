package br.com.mydrafts.apimydrafts.interceptors;

import br.com.mydrafts.apimydrafts.exceptions.BusinessException;
import br.com.mydrafts.apimydrafts.services.JWTService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static java.util.Objects.nonNull;

@Component
@AllArgsConstructor
public class AuthInterceptor implements HandlerInterceptor {

    private final JWTService jwtService;

    private static final Map<String, List<String>> whiteList = Map.of("POST", Arrays.asList("/v1/login", "/v1/users"));

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        try {
            if (verifyWhiteList(request.getMethod(), request.getRequestURI())) {
                return true;
            } else {
                var authorization = request.getHeader("Authorization");
                if (nonNull(authorization)) {
                    return jwtService.validateToken();
                }
                throw new BusinessException(
                    HttpStatus.UNAUTHORIZED.value(),
                    HttpStatus.UNAUTHORIZED.getReasonPhrase(),
                    HttpStatus.UNAUTHORIZED.toString()
                );
            }
        } catch (Exception e) {
            throw new BusinessException(
                HttpStatus.UNAUTHORIZED.value(),
                HttpStatus.UNAUTHORIZED.getReasonPhrase(),
                HttpStatus.UNAUTHORIZED.toString()
            );
        }
    }

    private boolean verifyWhiteList(String method, String uri) {
        if (Objects.nonNull(whiteList.get(method))) {
            return whiteList.get(method).stream().anyMatch(request -> request.contains(uri));
        }
        return false;
    }

}
