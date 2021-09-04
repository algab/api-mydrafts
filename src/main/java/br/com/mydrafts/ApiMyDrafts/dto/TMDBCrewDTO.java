package br.com.mydrafts.ApiMyDrafts.dto;

import br.com.mydrafts.ApiMyDrafts.config.ImageSerializer;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;

@Data
public class TMDBCrewDTO {

    private Integer id;

    private Integer gender;

    private String name;

    private String job;

    @JsonAlias({"profile_path"})
    @JsonSerialize(using = ImageSerializer.class)
    private String photo;

}
