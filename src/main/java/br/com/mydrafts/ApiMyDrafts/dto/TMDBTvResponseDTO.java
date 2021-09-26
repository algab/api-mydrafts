package br.com.mydrafts.ApiMyDrafts.dto;

import br.com.mydrafts.ApiMyDrafts.serializer.ImageSerializer;
import br.com.mydrafts.ApiMyDrafts.serializer.LocalDateSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
public class TMDBTvResponseDTO {

    private Integer id;

    private String title;

    private String titleOriginal;

    private String tagline;

    private String overview;

    @JsonSerialize(using = ImageSerializer.class)
    private String poster;

    @JsonSerialize(using = ImageSerializer.class)
    private String backdrop;

    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate dateRelease;

    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate lastEpisode;

    private String language;

    private List<String> created;

    private List<String> genres;

    private List<String> companies;

    private List<String> networks;

}
