package br.com.mydrafts.apimydrafts.dto;

import br.com.mydrafts.apimydrafts.constants.Gender;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserFormDTO {

    @Size(min = 3, message = "firstName needs at least 3 characters")
    @NotEmpty(message = "firstName is required")
    private String firstName;

    @Size(min = 3, message = "lastName needs at least 3 characters")
    @NotEmpty(message = "lastName is required")
    private String lastName;

    @Email(message = "email format is invalid")
    @NotEmpty(message = "email is required")
    private String email;

    @Size(min = 8, message = "password needs at least 8 characters")
    @NotEmpty(message = "password is required")
    private String password;

    @NotNull(message = "the gender field accepts the values: MALE and FEMALE")
    private Gender gender;

}
