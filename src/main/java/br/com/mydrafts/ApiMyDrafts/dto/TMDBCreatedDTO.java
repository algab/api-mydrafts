package br.com.mydrafts.ApiMyDrafts.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TMDBCreatedDTO {

    private Integer id;

    private String name;

    private Integer gender;

    @JsonAlias({"profile_path"})
    private String photo;

}
