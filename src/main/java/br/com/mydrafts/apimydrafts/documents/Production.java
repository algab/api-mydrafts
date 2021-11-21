package br.com.mydrafts.apimydrafts.documents;

import br.com.mydrafts.apimydrafts.constants.Media;
import br.com.mydrafts.apimydrafts.dto.tmdb.MovieResponseDTO;
import br.com.mydrafts.apimydrafts.dto.tmdb.TvResponseDTO;
import br.com.mydrafts.apimydrafts.dto.tmdb.TvSeasonDTO;
import lombok.*;
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

    private Media media;

    private MovieResponseDTO movie;

    private TvSeasonDTO tv;

}
