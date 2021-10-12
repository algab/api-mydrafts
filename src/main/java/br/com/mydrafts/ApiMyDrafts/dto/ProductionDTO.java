package br.com.mydrafts.ApiMyDrafts.dto;

import br.com.mydrafts.ApiMyDrafts.constants.Media;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductionDTO {

    private String id;

    private Integer tmdbID;

    private Media media;

    private TMDBMovieResponseDTO movie;

    private TMDBTvResponseDTO tv;

}
