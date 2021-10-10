package br.com.mydrafts.ApiMyDrafts.dto;

import br.com.mydrafts.ApiMyDrafts.constants.Gender;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Setter
@Getter
@Builder
public class UserFormDTO {

    @Size(min = 6, message = "name needs at least 6 characters")
    @NotEmpty(message = "name is required")
    private String name;

    @Email(message = "email format is invalid")
    @NotEmpty(message = "email is required")
    private String email;

    @Size(min = 6, message = "password needs at least 6 characters")
    @NotEmpty(message = "password is required")
    private String password;

    @NotNull(message = "gender is required")
    private Gender gender;

}
