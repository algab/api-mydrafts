package br.com.mydrafts.ApiMyDrafts.dto;

import br.com.mydrafts.ApiMyDrafts.constants.Gender;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserDTO {

    private String id;

    private String name;

    private String email;

    private Gender gender;

}
