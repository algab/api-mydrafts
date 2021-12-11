package br.com.mydrafts.apimydrafts.exceptions;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

@DisplayName("Tests for BusinessException")
class BusinessExceptionTest {

    private static final String ERROR_HTTP = "BAD REQUEST";
    private static final String MESSAGE_ERROR = "Bad Request";

    @Test
    @DisplayName("Test attributes BusinessException")
    void setAttributesBusinessExceptionShouldReturnSuccessful() {
        BusinessException exception = new BusinessException(HttpStatus.BAD_REQUEST.value(), ERROR_HTTP, MESSAGE_ERROR);
        Assertions.assertThat(HttpStatus.BAD_REQUEST.value()).isEqualTo(exception.getStatus());
        Assertions.assertThat(exception.getError()).isEqualTo(ERROR_HTTP);
        Assertions.assertThat(exception.getMessage()).isEqualTo(MESSAGE_ERROR);
    }

}
