package br.com.mydrafts.ApiMyDrafts.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductionDTO {

    private String id;

    private String tmdbID;

    private String media;

    private TMDBMovieResponseDTO movie;

    private TMDBTvResponseDTO tv;

}
