package br.com.mydrafts.apimydrafts.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ExceptionResponse {

    private Integer status;

    private String error;

    private String message;

}
