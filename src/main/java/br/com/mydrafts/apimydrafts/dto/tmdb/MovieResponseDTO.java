package br.com.mydrafts.apimydrafts.dto.tmdb;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Data
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class MovieResponseDTO extends BaseResponseDTO {

    private List<CrewDTO> crew;

}
