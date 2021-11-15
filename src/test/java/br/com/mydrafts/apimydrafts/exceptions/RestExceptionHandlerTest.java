package br.com.mydrafts.apimydrafts.exceptions;

import br.com.mydrafts.apimydrafts.exceptions.handler.RestExceptionHandler;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

@DisplayName("Tests for rest exception handler")
class RestExceptionHandlerTest {

    private RestExceptionHandler exceptionHandler = new RestExceptionHandler();

    @Test
    @DisplayName("Test handle business exception")
    void handleBusinessExceptionShouldReturnServerError() {
        BusinessException exception = new BusinessException(500, "Server Error", "Server Error");
        ResponseEntity response = exceptionHandler.handleBusinessException(exception);

        Assertions.assertThat(response.getStatusCodeValue()).isEqualTo(500);
    }

}
