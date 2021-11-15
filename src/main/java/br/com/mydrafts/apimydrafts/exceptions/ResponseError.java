package br.com.mydrafts.apimydrafts.exceptions;

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
public class ResponseError {

    private Integer status;

    private String error;

    private String message;

    private String timestamp;

}
