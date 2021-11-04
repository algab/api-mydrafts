package br.com.mydrafts.ApiMyDrafts.exceptions;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

@DisplayName("Tests for business exception")
public class BusinessExceptionTest {

    private static final String ERROR_HTTP = "BAD REQUEST";
    private static final String MESSAGE_ERROR = "Bad Request";

    @Test
    @DisplayName("Test attributes BusinessException")
    public void setAttributesBusinessExceptionShouldReturnSuccessful() {
        BusinessException exception = new BusinessException();
        exception.setStatus(HttpStatus.BAD_REQUEST.value());
        exception.setError(ERROR_HTTP);
        exception.setMessage(MESSAGE_ERROR);

        Assertions.assertThat(HttpStatus.BAD_REQUEST.value()).isEqualTo(exception.getStatus());
        Assertions.assertThat(ERROR_HTTP).isEqualTo(exception.getError());
        Assertions.assertThat(MESSAGE_ERROR).isEqualTo(exception.getMessage());
    }

}
