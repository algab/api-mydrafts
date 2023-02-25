package br.com.mydrafts.apimydrafts.documents;

import lombok.Data;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Document(collection = "draft")
public class DraftDocument extends BaseDocument {

    @Id
    private String id;

    private Integer rating;

    private Integer season;

    private String description;

    @DBRef
    private ProductionDocument production;

    @DBRef
    private UserDocument user;

}
