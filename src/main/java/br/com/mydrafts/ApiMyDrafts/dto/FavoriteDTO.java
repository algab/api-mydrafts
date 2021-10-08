package br.com.mydrafts.ApiMyDrafts.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class FavoriteDTO {

    private String id;

    private ProductionDTO production;

    private UserDTO user;

}
