package br.com.mydrafts.apimydrafts.dto;

import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DraftDTO {

    private String id;

    private String description;

    private Double rating;

    private ProductionDTO production;

}
