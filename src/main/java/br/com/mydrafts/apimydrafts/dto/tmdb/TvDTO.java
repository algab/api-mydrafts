package br.com.mydrafts.apimydrafts.dto.tmdb;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
public class TvDTO {

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

    private List<SeasonDTO> seasons;

    @JsonAlias({"created_by"})
    private List<CreatedDTO> created;

    private List<GenresDTO> genres;

    @JsonAlias({"production_companies"})
    private List<CompaniesDTO> companies;

    private List<NetworkDTO> networks;

}
