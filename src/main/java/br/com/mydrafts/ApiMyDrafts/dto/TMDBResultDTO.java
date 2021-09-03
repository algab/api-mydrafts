package br.com.mydrafts.ApiMyDrafts.dto;

import br.com.mydrafts.ApiMyDrafts.config.LocalDateSerializer;
import br.com.mydrafts.ApiMyDrafts.config.ImageSerializer;
import br.com.mydrafts.ApiMyDrafts.constants.Media;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
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
    @JsonSerialize(using = ImageSerializer.class)
    private String poster;

    @JsonAlias({"media_type"})
    private Media media;

    @JsonAlias({"release_date", "first_air_date"})
    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate dateRelease;

    @JsonAlias({"original_language"})
    private String language;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Long popularity;

}
