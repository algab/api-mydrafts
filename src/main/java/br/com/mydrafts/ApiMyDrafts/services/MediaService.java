package br.com.mydrafts.ApiMyDrafts.services;

import br.com.mydrafts.ApiMyDrafts.dto.TMDBMovieResponseDTO;
import br.com.mydrafts.ApiMyDrafts.dto.TMDBTvResponseDTO;

public interface MediaService {

    TMDBMovieResponseDTO getMovie(Integer id);

    TMDBTvResponseDTO getTV(Integer id);

}
