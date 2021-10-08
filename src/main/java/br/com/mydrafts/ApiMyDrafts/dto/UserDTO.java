package br.com.mydrafts.ApiMyDrafts.dto;

import br.com.mydrafts.ApiMyDrafts.constants.Gender;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

    private String id;

    private String name;

    private String email;

    private Gender gender;

}
