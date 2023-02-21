package br.com.mydrafts.apimydrafts.documents;

import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
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
