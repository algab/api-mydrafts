package br.com.mydrafts.ApiMyDrafts.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class TMDBResponseTvDTO {
    private Integer id;
    private String name;
    private String nameOriginal;
    private String overview;
    private String language;
    private String posterPath;
    private String mediaType;
    private LocalDate dateFirstAir;
}
