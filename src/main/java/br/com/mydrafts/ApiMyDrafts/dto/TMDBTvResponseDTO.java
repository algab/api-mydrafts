package br.com.mydrafts.ApiMyDrafts.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
public class TMDBTvResponseDTO {

    private Integer id;

    private String title;

    private String titleOriginal;

    private String tagline;

    private String overview;

    private String poster;

    private String backdrop;

    private LocalDate dateRelease;

    private String language;

    private List<String> genres;

    private List<String> companies;

    private List<String> networks;

}
