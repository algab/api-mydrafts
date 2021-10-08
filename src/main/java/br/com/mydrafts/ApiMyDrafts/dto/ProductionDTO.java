package br.com.mydrafts.ApiMyDrafts.dto;

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

    private String media;

    private TMDBMovieResponseDTO movie;

    private TMDBTvResponseDTO tv;

}
