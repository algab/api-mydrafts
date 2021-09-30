package br.com.mydrafts.ApiMyDrafts.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DraftFormDTO {

    private String description;

    private Double rating;

    private String tmdbID;

    private String media;

    private String userID;

}
