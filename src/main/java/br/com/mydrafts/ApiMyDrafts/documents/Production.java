package br.com.mydrafts.ApiMyDrafts.documents;

import br.com.mydrafts.ApiMyDrafts.dto.TMDBMovieResponseDTO;
import br.com.mydrafts.ApiMyDrafts.dto.TMDBTvResponseDTO;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "production")
public class Production {

    @Id
    private String id;

    private String tmdbID;

    private String media;

    private TMDBMovieResponseDTO movie;

    private TMDBTvResponseDTO tv;

}
