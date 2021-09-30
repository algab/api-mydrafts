package br.com.mydrafts.ApiMyDrafts.documents;

import br.com.mydrafts.ApiMyDrafts.constants.Gender;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Document(collection = "user")
public class User {

    @Id
    private String id;

    private String name;

    private String email;

    private String password;

    private Gender gender;

}
