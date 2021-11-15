package br.com.mydrafts.apimydrafts.exceptions;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import java.time.LocalDate;

@DisplayName("Tests for response default")
class ResponseErrorTest {

    private static final String ERROR_HTTP = "BAD REQUEST";
    private static final String MESSAGE_ERROR = "Bad Request";

    @Test
    @DisplayName("Test attributes ResponseError")
    void setAttributesResponseErrorShouldReturnSuccessful() {
        ResponseError exception = new ResponseError();
        exception.setStatus(HttpStatus.BAD_REQUEST.value());
        exception.setError(ERROR_HTTP);
        exception.setMessage(MESSAGE_ERROR);
        exception.setTimestamp(LocalDate.now().toString());

        Assertions.assertThat(HttpStatus.BAD_REQUEST.value()).isEqualTo(exception.getStatus());
        Assertions.assertThat(exception.getError()).isEqualTo(ERROR_HTTP);
        Assertions.assertThat(exception.getMessage()).isEqualTo(MESSAGE_ERROR);
    }

}
