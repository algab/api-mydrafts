package br.com.mydrafts.ApiMyDrafts.exceptions;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResponseException {

    private Integer status;

    private String error;

    private String message;

    private String timestamp;

}
