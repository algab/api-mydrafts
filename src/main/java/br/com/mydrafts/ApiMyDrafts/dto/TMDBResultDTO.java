package br.com.mydrafts.ApiMyDrafts.dto;

import br.com.mydrafts.ApiMyDrafts.mapper.PosterMapper;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;

import java.time.LocalDate;

@Data
public class TMDBResultDTO {

    private Integer id;

    @JsonAlias({"name"})
    private String title;

    @JsonAlias({"original_title", "original_name"})
    private String titleOriginal;

    @JsonAlias({"poster_path"})
    @JsonSerialize(using = PosterMapper.class)
    private String poster;

    @JsonAlias({"media_type"})
    private String media;

    @JsonAlias({"release_date", "first_air_date"})
    private LocalDate dateRelease;

    @JsonAlias({"original_language"})
    private String language;

}
