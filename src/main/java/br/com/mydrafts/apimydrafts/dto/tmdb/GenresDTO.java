package br.com.mydrafts.apimydrafts.dto.tmdb;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GenresDTO {

    private Integer id;

    private String name;

}
