package br.com.mydrafts.ApiMyDrafts.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BusinessException extends RuntimeException {

    private Integer status;

    private String error;

    private String message;

}
