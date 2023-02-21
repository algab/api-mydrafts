package br.com.mydrafts.apimydrafts.dto.tmdb;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
public class MovieDTO {

    private Integer id;

    @JsonAlias({"name"})
    private String title;

    @JsonAlias({"original_title"})
    private String titleOriginal;

    private String tagline;

    private String overview;

    @JsonAlias({"poster_path"})
    private String poster;

    @JsonAlias({"backdrop_path"})
    private String backdrop;

    @JsonAlias({"release_date"})
    private LocalDate dateRelease;

    @JsonAlias({"original_language"})
    private String language;

    private List<GenreDTO> genres;

    @JsonAlias({"production_companies"})
    private List<CompanyDTO> companies;

}
