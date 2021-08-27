package br.com.mydrafts.ApiMyDrafts.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class TMDBResponseDTO {
    private Integer id;
    private String title;
    private String titleOriginal;
    private String overview;
    private String language;
    private String posterPath;
    private String mediaType;
    private LocalDate dateRelease;
}
