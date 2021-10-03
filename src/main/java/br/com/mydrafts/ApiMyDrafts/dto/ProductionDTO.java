package br.com.mydrafts.ApiMyDrafts.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductionDTO {

    private String id;

    private Integer tmdbID;

    private String media;

    private TMDBMovieResponseDTO movie;

    private TMDBTvResponseDTO tv;

}
