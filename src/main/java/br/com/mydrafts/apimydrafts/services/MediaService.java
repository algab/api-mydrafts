package br.com.mydrafts.apimydrafts.services;

import br.com.mydrafts.apimydrafts.dto.tmdb.MovieResponseDTO;
import br.com.mydrafts.apimydrafts.dto.tmdb.TvResponseDTO;

public interface MediaService {

    MovieResponseDTO getMovie(Integer id);

    TvResponseDTO getTV(Integer id);

}
