package br.com.mydrafts.ApiMyDrafts.dto;

import lombok.Data;

import java.util.List;

@Data
public class TMDBCreditsDTO {

    private Integer id;

    private List<TMDBCrewDTO> crew;

}
