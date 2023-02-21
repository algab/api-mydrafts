package br.com.mydrafts.apimydrafts.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DraftDTO {

    private String id;

    private Integer rating;

    private String description;

    private Integer season;

    private ProductionDTO production;

}
