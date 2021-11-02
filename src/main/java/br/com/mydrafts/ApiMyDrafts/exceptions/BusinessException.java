package br.com.mydrafts.ApiMyDrafts.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class BusinessException extends RuntimeException {

    private Integer status;

    private String error;

    private String message;

}
