package br.com.mydrafts.apimydrafts.dto.tmdb;

import br.com.mydrafts.apimydrafts.serializer.ImageSerializer;
import br.com.mydrafts.apimydrafts.serializer.LocalDateSerializer;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class SeasonDTO {

    private String name;

    @JsonAlias({"season_number"})
    private Integer number;

    @JsonAlias({"episode_count"})
    private Integer totalEpisodes;

    private String overview;

    @JsonAlias({"air_date"})
    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate date;

    @JsonAlias({"poster_path"})
    @JsonSerialize(using = ImageSerializer.class)
    private String poster;

}
