package br.com.mydrafts.ApiMyDrafts.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DraftDTO {

    private String id;

    private String description;

    private Double rating;

    private ProductionDTO production;

    private UserDTO user;

}
