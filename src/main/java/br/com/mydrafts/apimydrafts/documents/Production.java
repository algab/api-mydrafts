package br.com.mydrafts.apimydrafts.documents;

import br.com.mydrafts.apimydrafts.constants.Media;
import br.com.mydrafts.apimydrafts.dto.TMDBMovieResponseDTO;
import br.com.mydrafts.apimydrafts.dto.TMDBTvResponseDTO;
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

    private TMDBMovieResponseDTO movie;

    private TMDBTvResponseDTO tv;

}
