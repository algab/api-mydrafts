package br.com.mydrafts.apimydrafts.documents;

import lombok.Data;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import br.com.mydrafts.apimydrafts.constants.Media;
import br.com.mydrafts.apimydrafts.dto.tmdb.BaseProductionDTO;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "production")
@EqualsAndHashCode(callSuper = true)
public class ProductionDocument extends BaseDocument {

    @Id
    private String id;

    private Integer tmdbID;

    private Media media;

    private BaseProductionDTO data;

}
