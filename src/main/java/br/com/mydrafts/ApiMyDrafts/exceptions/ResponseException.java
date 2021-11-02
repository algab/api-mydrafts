package br.com.mydrafts.ApiMyDrafts.exceptions;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class ResponseException {

    private Integer status;

    private String error;

    private String message;

    private String timestamp;

}
