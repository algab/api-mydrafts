package br.com.mydrafts.apimydrafts.dto;

import br.com.mydrafts.apimydrafts.serializer.ImageSerializer;
import br.com.mydrafts.apimydrafts.serializer.LocalDateSerializer;
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

    private List<TMDBSeasonDTO> seasons;

    private List<String> created;

    private List<String> genres;

    private List<String> companies;

    private List<String> networks;

}
