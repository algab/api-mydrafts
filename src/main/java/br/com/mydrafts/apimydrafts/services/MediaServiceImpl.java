package br.com.mydrafts.apimydrafts.services;

import br.com.mydrafts.apimydrafts.clients.TMDBProxy;
import br.com.mydrafts.apimydrafts.dto.tmdb.CreditsDTO;
import br.com.mydrafts.apimydrafts.dto.tmdb.MovieDTO;
import br.com.mydrafts.apimydrafts.dto.tmdb.MovieResponseDTO;
import br.com.mydrafts.apimydrafts.dto.tmdb.TvDTO;
import br.com.mydrafts.apimydrafts.dto.tmdb.TvResponseDTO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class MediaServiceImpl implements MediaService {

    private TMDBProxy tmdbProxy;

    private ModelMapper mapper;

    @Override
    public MovieResponseDTO getMovie(Integer id) {
        MovieDTO movie = this.tmdbProxy.getMovie(id);
        CreditsDTO credits = this.tmdbProxy.getMovieCredits(id);
        MovieResponseDTO response = mapper.map(movie, MovieResponseDTO.class);
        response.setCrew(credits.getCrew());
        return response;
    }

    @Override
    public TvResponseDTO getTV(Integer id) {
        TvDTO tv = this.tmdbProxy.getTV(id);
        return mapper.map(tv, TvResponseDTO.class);
    }

}
