package br.com.mydrafts.apimydrafts.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class TMDBCreditsDTO {

    private Integer id;

    private List<TMDBCrewDTO> crew;

}
