package br.com.mydrafts.apimydrafts.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DraftDTO {

    private String id;

    private String description;

    private Double rating;

    private ProductionDTO production;

    private UserDTO user;

}
