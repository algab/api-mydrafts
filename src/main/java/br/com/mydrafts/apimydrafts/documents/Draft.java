package br.com.mydrafts.apimydrafts.documents;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "draft")
public class Draft {

    @Id
    private String id;

    private String description;

    private Double rating;

    @DBRef
    private Production production;

    @DBRef
    private User user;

}
