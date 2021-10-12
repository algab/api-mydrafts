package br.com.mydrafts.ApiMyDrafts.dto;

import br.com.mydrafts.ApiMyDrafts.constants.Media;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Builder
public class FavoriteFormDTO {

    @NotNull(message = "tmdbID is required")
    private Integer tmdbID;

    @NotNull(message = "media is required")
    private Media media;

    @NotEmpty(message = "userID is required")
    private String userID;

}
