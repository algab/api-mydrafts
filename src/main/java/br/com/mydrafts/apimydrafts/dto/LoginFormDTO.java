package br.com.mydrafts.apimydrafts.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginFormDTO {

    @Email(message = "email format is invalid")
    @NotEmpty(message = "email is required")
    private String email;

    @Size(min = 8, message = "password needs at least 8 characters")
    @NotEmpty(message = "password is required")
    private String password;

}
