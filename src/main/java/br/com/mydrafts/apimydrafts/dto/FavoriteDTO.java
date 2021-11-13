package br.com.mydrafts.apimydrafts.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FavoriteDTO {

    private String id;

    private ProductionDTO production;

    private UserDTO user;

}
