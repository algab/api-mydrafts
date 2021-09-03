package br.com.mydrafts.ApiMyDrafts.dto;

import br.com.mydrafts.ApiMyDrafts.config.LocalDateSerializer;
import br.com.mydrafts.ApiMyDrafts.config.ImageSerializer;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class TMDBMovieDTO {

    private Integer id;

    @JsonAlias({"name"})
    private String title;

    @JsonAlias({"original_title"})
    private String titleOriginal;

    private String tagline;

    private String overview;

    @JsonAlias({"poster_path"})
    @JsonSerialize(using = ImageSerializer.class)
    private String poster;

    @JsonAlias({"backdrop_path"})
    @JsonSerialize(using = ImageSerializer.class)
    private String backdrop;

    @JsonAlias({"release_date"})
    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate dateRelease;

    @JsonAlias({"original_language"})
    private String language;

    private List<TMDBGenresDTO> genres;

    @JsonAlias({"production_companies"})
    private List<TMDBCompaniesDTO> companies;

}
