package br.com.mydrafts.ApiMyDrafts.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TMDBResponseDTO {

    private List<TMDBResultDTO> results;

}
