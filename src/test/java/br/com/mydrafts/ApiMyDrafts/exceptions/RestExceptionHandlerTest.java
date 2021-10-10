package br.com.mydrafts.ApiMyDrafts.exceptions;

import br.com.mydrafts.ApiMyDrafts.exceptions.handler.RestExceptionHandler;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

@DisplayName("Tests for rest exception handler")
public class RestExceptionHandlerTest {

    private RestExceptionHandler exceptionHandler = new RestExceptionHandler();

    @Test
    @DisplayName("Test handle business exception")
    public void handleBusinessExceptionShouldReturnServerError() {
        BusinessException exception = new BusinessException(500, "Server Error", "Server Error");
        ResponseEntity response = exceptionHandler.handleBusinessException(exception);

        Assertions.assertThat(response.getStatusCodeValue()).isEqualTo(500);
    }

}
