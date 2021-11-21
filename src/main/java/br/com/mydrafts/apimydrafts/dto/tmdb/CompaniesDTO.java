package br.com.mydrafts.apimydrafts.dto.tmdb;

import br.com.mydrafts.apimydrafts.serializer.ImageSerializer;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CompaniesDTO {

    private Integer id;

    private String name;

    @JsonAlias({"logo_path"})
    @JsonSerialize(using = ImageSerializer.class)
    private String logo;

    @JsonAlias({"origin_country"})
    private String country;

}
