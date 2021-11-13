package br.com.mydrafts.apimydrafts.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class BusinessException extends RuntimeException {

    private final Integer status;

    private final String error;

    private final String message;

}
