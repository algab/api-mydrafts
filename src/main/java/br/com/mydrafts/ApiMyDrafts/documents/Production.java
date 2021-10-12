package br.com.mydrafts.ApiMyDrafts.documents;

import br.com.mydrafts.ApiMyDrafts.constants.Media;
import br.com.mydrafts.ApiMyDrafts.dto.TMDBMovieResponseDTO;
import br.com.mydrafts.ApiMyDrafts.dto.TMDBTvResponseDTO;
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
