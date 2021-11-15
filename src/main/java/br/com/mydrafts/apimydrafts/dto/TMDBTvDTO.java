package br.com.mydrafts.apimydrafts.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
public class TMDBTvDTO {

    private Integer id;

    @JsonAlias({"name"})
    private String title;

    @JsonAlias({"original_name"})
    private String titleOriginal;

    private String tagline;

    private String overview;

    @JsonAlias({"poster_path"})
    private String poster;

    @JsonAlias({"backdrop_path"})
    private String backdrop;

    @JsonAlias({"first_air_date"})
    private LocalDate dateRelease;

    @JsonAlias({"last_air_date"})
    private LocalDate lastEpisode;

    @JsonAlias({"original_language"})
    private String language;

    private List<TMDBSeasonDTO> seasons;

    @JsonAlias({"created_by"})
    private List<TMDBCreatedDTO> created;

    private List<TMDBGenresDTO> genres;

    @JsonAlias({"production_companies"})
    private List<TMDBCompaniesDTO> companies;

    private List<TMDBNetworkDTO> networks;

}
