package br.com.mydrafts.apimydrafts.dto.tmdb;

import br.com.mydrafts.apimydrafts.serializer.LocalDateSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.util.List;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class TvResponseDTO extends BaseProductionDTO {

    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate lastEpisode;

    private List<SeasonDTO> seasons;

    private List<String> created;

    private List<String> networks;

}
