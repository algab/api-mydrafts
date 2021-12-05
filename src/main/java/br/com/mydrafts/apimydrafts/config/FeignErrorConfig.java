package br.com.mydrafts.apimydrafts.config;

import br.com.mydrafts.apimydrafts.exceptions.BusinessException;

import feign.Response;
import feign.codec.ErrorDecoder;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;

@Configuration
public class FeignErrorConfig implements ErrorDecoder {

    @Override
    public Exception decode(String s, Response response) {
        return new BusinessException(response.status(), HttpStatus.valueOf(response.status()).toString(), response.reason());
    }

}
