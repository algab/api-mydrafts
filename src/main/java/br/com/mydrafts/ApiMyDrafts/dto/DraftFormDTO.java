package br.com.mydrafts.ApiMyDrafts.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class DraftFormDTO {

    @NotEmpty(message = "description is required")
    private String description;

    @NotNull(message = "rating is required")
    private Double rating;

    @NotEmpty(message = "media is required")
    private String media;

    @NotNull(message = "tmdbID is required")
    private Integer tmdbID;

    @NotEmpty(message = "userID is required")
    private String userID;

}
