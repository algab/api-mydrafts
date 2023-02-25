package br.com.mydrafts.apimydrafts.documents;

import lombok.Data;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import br.com.mydrafts.apimydrafts.constants.Gender;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "user")
@EqualsAndHashCode(callSuper = true)
public class UserDocument extends BaseDocument {

    @Id
    private String id;

    private String firstName;

    private String lastName;

    private String email;

    private String password;

    private Gender gender;

}
