package br.com.mydrafts.ApiMyDrafts.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginDTO {

    private String token;

    private UserDTO user;

}
