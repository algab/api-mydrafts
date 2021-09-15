package br.com.mydrafts.ApiMyDrafts.dto;

import br.com.mydrafts.ApiMyDrafts.config.ImageSerializer;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TMDBCompaniesDTO {

    private Integer id;

    private String name;

    @JsonAlias({"logo_path"})
    @JsonSerialize(using = ImageSerializer.class)
    private String logo;

    @JsonAlias({"origin_country"})
    private String country;

}
