package br.com.mydrafts.apimydrafts.dto.tmdb;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreatedDTO {

    private Integer id;

    private String name;

    private Integer gender;

    @JsonAlias({"profile_path"})
    private String photo;

}
