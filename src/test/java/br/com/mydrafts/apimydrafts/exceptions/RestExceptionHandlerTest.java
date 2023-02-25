package br.com.mydrafts.apimydrafts.exceptions;

import br.com.mydrafts.apimydrafts.exceptions.handler.RestExceptionHandler;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Tests for rest exception handler")
class RestExceptionHandlerTest {

    private final RestExceptionHandler exceptionHandler = new RestExceptionHandler();

    @Test
    @DisplayName("Test handle business exception")
    void handleBusinessExceptionShouldReturnServerError() {
        BusinessException exception = new BusinessException(500, "Server Error", "Server Error");
        ResponseEntity<ExceptionResponse> response = exceptionHandler.handleBusinessException(exception);

        assertThat(response.getStatusCodeValue()).isEqualTo(500);
    }

}
