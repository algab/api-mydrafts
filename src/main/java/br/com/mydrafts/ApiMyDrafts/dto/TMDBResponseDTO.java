package br.com.mydrafts.ApiMyDrafts.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class TMDBResponseDTO {

    private List<TMDBResultDTO> results;

}
