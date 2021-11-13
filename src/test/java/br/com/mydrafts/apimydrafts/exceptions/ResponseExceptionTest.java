package br.com.mydrafts.apimydrafts.exceptions;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import java.time.LocalDate;

@DisplayName("Tests for response exception")
public class ResponseExceptionTest {

    private static final String ERROR_HTTP = "BAD REQUEST";
    private static final String MESSAGE_ERROR = "Bad Request";

    @Test
    @DisplayName("Test attributes ResponseException")
    public void setAttributesResponseExceptionShouldReturnSuccessful() {
        ResponseException exception = new ResponseException();
        exception.setStatus(HttpStatus.BAD_REQUEST.value());
        exception.setError(ERROR_HTTP);
        exception.setMessage(MESSAGE_ERROR);
        exception.setTimestamp(LocalDate.now().toString());

        Assertions.assertThat(HttpStatus.BAD_REQUEST.value()).isEqualTo(exception.getStatus());
        Assertions.assertThat(ERROR_HTTP).isEqualTo(exception.getError());
        Assertions.assertThat(MESSAGE_ERROR).isEqualTo(exception.getMessage());
    }

}
