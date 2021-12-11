package br.com.mydrafts.apimydrafts.dto;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FavoriteDTO {

    private String id;

    private ProductionDTO production;

}
