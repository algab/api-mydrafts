package br.com.mydrafts.ApiMyDrafts.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Getter
@Setter
public class LoginFormDTO {

    @Email(message = "email format is invalid")
    @NotEmpty(message = "email is required")
    private String email;

    @Size(min = 6, message = "password needs at least 6 characters")
    @NotEmpty(message = "password is required")
    private String password;

}
