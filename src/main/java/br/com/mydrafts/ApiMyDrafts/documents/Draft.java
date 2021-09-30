package br.com.mydrafts.ApiMyDrafts.documents;

import br.com.mydrafts.ApiMyDrafts.dto.UserDTO;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Document(collection = "draft")
public class Draft {

    @Id
    private String id;

    private String description;

    private Double rating;

    private Production production;

    private UserDTO user;

}
