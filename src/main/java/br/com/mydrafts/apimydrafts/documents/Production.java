package br.com.mydrafts.apimydrafts.documents;

import lombok.*;
import br.com.mydrafts.apimydrafts.constants.Media;
import br.com.mydrafts.apimydrafts.dto.tmdb.BaseResponseDTO;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "production")
public class Production {

    @Id
    private String id;

    private Integer tmdbID;

    private Integer season;

    private Media media;

    private BaseResponseDTO data;

}
