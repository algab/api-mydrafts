package br.com.mydrafts.apimydrafts.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LoginFormDTO {

    @Email(message = "email format is invalid")
    @NotEmpty(message = "email is required")
    private String email;

    @Size(min = 6, message = "password needs at least 6 characters")
    @NotEmpty(message = "password is required")
    private String password;

}
