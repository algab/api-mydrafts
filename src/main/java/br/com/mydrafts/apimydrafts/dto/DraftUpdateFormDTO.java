package br.com.mydrafts.apimydrafts.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DraftUpdateFormDTO {

    @NotEmpty(message = "userID is required")
    private String userID;

    @Min(value = 1, message = "minimum rating is 1")
    @Max(value = 5, message = "maximum rating is 5")
    @NotNull(message = "rating is required")
    private Integer rating;

    private Integer season;

    private String description;

}
