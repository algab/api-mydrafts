package br.com.mydrafts.ApiMyDrafts.services;

import br.com.mydrafts.ApiMyDrafts.dto.TMDBMovieDTO;

public interface MediaService {

    TMDBMovieDTO getMovie(Integer id);

}
