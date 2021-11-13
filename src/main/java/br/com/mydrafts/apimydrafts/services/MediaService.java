package br.com.mydrafts.apimydrafts.services;

import br.com.mydrafts.apimydrafts.dto.TMDBMovieResponseDTO;
import br.com.mydrafts.apimydrafts.dto.TMDBTvResponseDTO;

public interface MediaService {

    TMDBMovieResponseDTO getMovie(Integer id);

    TMDBTvResponseDTO getTV(Integer id);

}
