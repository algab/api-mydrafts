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
public class SeasonResponseDTO extends BaseResponseDTO {

    private Integer season;

    private List<String> created;

    private List<String> networks;

}
