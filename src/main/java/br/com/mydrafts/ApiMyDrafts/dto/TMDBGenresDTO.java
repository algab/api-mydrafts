package br.com.mydrafts.ApiMyDrafts.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TMDBGenresDTO {

    private Integer id;

    private String name;

}
