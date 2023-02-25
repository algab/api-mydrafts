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
@Document(collection = "favorite")
public class FavoriteDocument extends BaseDocument {

    @Id
    private String id;

    @DBRef
    private ProductionDocument production;

    @DBRef
    private UserDocument user;

}
