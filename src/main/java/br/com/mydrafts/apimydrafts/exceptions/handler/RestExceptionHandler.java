package br.com.mydrafts.apimydrafts.exceptions.handler;

import br.com.mydrafts.apimydrafts.exceptions.BusinessException;
import br.com.mydrafts.apimydrafts.exceptions.ResponseDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ResponseDefault> handleBusinessException(BusinessException exception) {
        ResponseDefault responseException = ResponseDefault.builder()
                .status(exception.getStatus())
                .error(exception.getError())
                .message(exception.getMessage())
                .timestamp(LocalDateTime.now().toString())
                .build();
        return ResponseEntity.status(exception.getStatus()).body(responseException);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request
    ) {
        String message = ex.getFieldErrors().stream()
                .map(FieldError::getDefaultMessage)
                .collect(Collectors.joining(", "));
        ResponseDefault responseException = ResponseDefault.builder()
                .status(status.value())
                .error(HttpStatus.BAD_REQUEST.toString())
                .message(message)
                .timestamp(LocalDateTime.now().toString())
                .build();
        return ResponseEntity.status(status.value()).body(responseException);
    }

}
