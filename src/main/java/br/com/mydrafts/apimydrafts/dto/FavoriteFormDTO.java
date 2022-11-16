package br.com.mydrafts.apimydrafts.dto;

import br.com.mydrafts.apimydrafts.constants.Media;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FavoriteFormDTO {

    @NotNull(message = "tmdbID is required")
    private Integer tmdbID;

    @NotNull(message = "media is required")
    private Media media;

    @NotEmpty(message = "userID is required")
    private String userID;

}
