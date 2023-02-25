package br.com.mydrafts.apimydrafts.exceptions;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Tests for exception response")
class ExceptionResponseTest {

    private static final String ERROR_HTTP = "BAD REQUEST";
    private static final String MESSAGE_ERROR = "Bad Request";

    @Test
    @DisplayName("Test attributes ExceptionResponse")
    void setAttributesExceptionResponseShouldReturnSuccessful() {
        ExceptionResponse exception = new ExceptionResponse(
            HttpStatus.BAD_REQUEST.value(),
            ERROR_HTTP,
            MESSAGE_ERROR
        );

        assertThat(HttpStatus.BAD_REQUEST.value()).isEqualTo(exception.getStatus());
        assertThat(exception.getError()).isEqualTo(ERROR_HTTP);
        assertThat(exception.getMessage()).isEqualTo(MESSAGE_ERROR);
    }

}
