package br.com.mydrafts.apimydrafts.dto.tmdb;

import br.com.mydrafts.apimydrafts.serializer.ImageSerializer;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CrewDTO {

    private Integer id;

    private Integer gender;

    private String name;

    private String job;

    @JsonAlias({"profile_path"})
    @JsonSerialize(using = ImageSerializer.class)
    private String photo;

}
