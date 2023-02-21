package br.com.mydrafts.apimydrafts.exceptions;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

@DisplayName("Tests for exception response")
class ExceptionResponseTest {

    private static final String ERROR_HTTP = "BAD REQUEST";
    private static final String MESSAGE_ERROR = "Bad Request";

    @Test
    @DisplayName("Test attributes ExceptionResponse")
    void setAttributesExceptionResponseShouldReturnSuccessful() {
        ExceptionResponse exception = new ExceptionResponse();
        exception.setStatus(HttpStatus.BAD_REQUEST.value());
        exception.setError(ERROR_HTTP);
        exception.setMessage(MESSAGE_ERROR);

        Assertions.assertThat(HttpStatus.BAD_REQUEST.value()).isEqualTo(exception.getStatus());
        Assertions.assertThat(exception.getError()).isEqualTo(ERROR_HTTP);
        Assertions.assertThat(exception.getMessage()).isEqualTo(MESSAGE_ERROR);
    }

}
