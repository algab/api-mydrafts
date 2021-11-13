package br.com.mydrafts.apimydrafts.documents;

import br.com.mydrafts.apimydrafts.constants.Gender;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "user")
public class User {

    @Id
    private String id;

    private String name;

    private String email;

    private String password;

    private Gender gender;

}
