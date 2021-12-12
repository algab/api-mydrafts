package br.com.mydrafts.apimydrafts.config;

import feign.Request;
import feign.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import java.util.HashMap;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Tests for FeignErrorConfig")
class FeignErrorConfigTest {

    private FeignErrorConfig feignError = new FeignErrorConfig();

    @Test
    void decodeFeignShouldDecodeResponse() {
        Request request = Request.create(Request.HttpMethod.GET, "/test", new HashMap<>(), null, null, null);
        Response response = Response.builder().status(500).reason(HttpStatus.INTERNAL_SERVER_ERROR.toString()).request(request).headers(new HashMap<>()).build();

        Exception exception = feignError.decode("", response);

        assertThat(HttpStatus.INTERNAL_SERVER_ERROR.toString()).hasToString(exception.getMessage());
    }

}
