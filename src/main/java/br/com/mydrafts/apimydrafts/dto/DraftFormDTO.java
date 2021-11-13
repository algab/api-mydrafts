package br.com.mydrafts.apimydrafts.dto;

import br.com.mydrafts.apimydrafts.constants.Media;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Builder
public class DraftFormDTO {

    private String description;

    @Min(value = 1, message = "minimum rating is 1")
    @Max(value = 10, message = "maximum rating is 10")
    @NotNull(message = "rating is required")
    private Double rating;

    @NotNull(message = "media is required")
    private Media media;

    @NotNull(message = "tmdbID is required")
    private Integer tmdbID;

    @NotEmpty(message = "userID is required")
    private String userID;

}
