package br.com.mydrafts.ApiMyDrafts.documents;

import br.com.mydrafts.ApiMyDrafts.dto.UserDTO;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "favorite")
public class Favorite {

    @Id
    private String id;

    private Production production;

    private UserDTO user;

}