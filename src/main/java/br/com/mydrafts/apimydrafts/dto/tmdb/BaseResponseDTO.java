package br.com.mydrafts.apimydrafts.dto.tmdb;

import br.com.mydrafts.apimydrafts.serializer.ImageSerializer;
import br.com.mydrafts.apimydrafts.serializer.LocalDateSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.util.List;

@Data
@SuperBuilder
@NoArgsConstructor
public class BaseResponseDTO {

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

    private String language;

    private List<String> genres;

    private List<String> companies;

}
