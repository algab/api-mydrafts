package br.com.mydrafts.ApiMyDrafts.exceptions;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ResponseException {

    private Integer status;

    private String error;

    private String message;

    private String timestamp;

}
