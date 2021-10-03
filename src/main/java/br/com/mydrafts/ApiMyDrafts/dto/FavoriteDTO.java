package br.com.mydrafts.ApiMyDrafts.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FavoriteDTO {

    private String id;

    private ProductionDTO production;

    private UserDTO user;

}
