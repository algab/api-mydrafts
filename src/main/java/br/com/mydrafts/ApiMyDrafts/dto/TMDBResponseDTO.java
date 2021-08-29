package br.com.mydrafts.ApiMyDrafts.dto;

import lombok.Data;

import java.util.List;

@Data
public class TMDBResponseDTO {
    private List<TMDBResultDTO> results;
}
