package br.com.mydrafts.apimydrafts.documents;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "favorite")
public class Favorite {

    @Id
    private String id;

    @DBRef
    private Production production;

    @DBRef
    private User user;

}
