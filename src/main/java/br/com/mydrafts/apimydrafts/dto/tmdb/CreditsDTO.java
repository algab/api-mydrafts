package br.com.mydrafts.apimydrafts.dto.tmdb;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class CreditsDTO {

    private Integer id;

    private List<CrewDTO> crew;

}
