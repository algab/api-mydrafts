package br.com.mydrafts.apimydrafts.dto;

import br.com.mydrafts.apimydrafts.constants.Media;
import br.com.mydrafts.apimydrafts.dto.tmdb.MovieResponseDTO;
import br.com.mydrafts.apimydrafts.dto.tmdb.TvResponseDTO;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductionDTO {

    private String id;

    private Integer tmdbID;

    private Media media;

    private MovieResponseDTO movie;

    private TvResponseDTO tv;

}
