package br.com.mydrafts.apimydrafts.dto.tmdb;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResponseDTO {

    private List<ResultDTO> results;

}
